package com.xming.sbplaceholder2.parser;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.event.GlobalVariablesLoadEvent;
import com.xming.sbplaceholder2.parser.type.SBElement;
import com.xming.sbplaceholder2.parser.type.element.*;
import com.xming.sbplaceholder2.parser.type.type.ExpressionType;
import me.clip.placeholderapi.PlaceholderAPI;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Parser {
    private final String raw_expression;
    private SBElement<?> expression;
    private HashMap<String, SBElement<?>> variables;
    public final int debug;
    public int depth;
    static private HashMap<String, SBElement<?>> global_variables = new HashMap<>();

    private final long startTime = System.currentTimeMillis();

    public static void loadGlobalVariables() {
        global_variables = new HashMap<>();
        global_variables.put("debug", new FunctionElement((Parser parser, SBElement<?>[] inst) -> new StringElement(inst[0].toDebug())));
        global_variables.put("random", new FunctionElement((Parser parser, SBElement<?>[] inst) -> new NumberElement(Math.random())));
        global_variables.put("getAuthor", new FunctionElement((Parser parser, SBElement<?>[] inst) -> new StringElement("xming_jun")));
        global_variables.put("if", new FunctionElement((Parser parser, SBElement<?>[] inst) -> inst[0].asBool().toBool() ? inst[1] : inst[2]));
        global_variables.put("range", new FunctionElement((Parser parser, SBElement<?>[] inst) -> {
            long start = 0, end = 0, step = 1;
            if (inst.length == 1) {
                end = inst[0].asInt().value;
            } else if (inst.length == 2) {
                start = inst[0].asInt().value;
                end = inst[1].asInt().value;
            } else if (inst.length == 3) {
                start = inst[0].asInt().value;
                end = inst[1].asInt().value;
                step = inst[2].asInt().value;
            }
            ListElement listInst = new ListElement();
            for (long i = start; i < end; i += step) listInst.append(new IntElement(i));
            return listInst;
        }));
        global_variables.put("online", new FunctionElement((Parser parser, SBElement<?>[] inst) -> {
            ListElement listInst = new ListElement();
            listInst.addAll(Bukkit.getOnlinePlayers().stream().map(PlayerElement::new).toArray(SBElement[]::new));
            return listInst;
        }));
        global_variables.put("round", new FunctionElement((Parser parser, SBElement<?>[] inst) -> {
            long accuracy = inst.length > 1 ? inst[1].asInt().value : 0;
            double value = inst[0].asNumber().value;
            if (accuracy == 0) {
                return new IntElement(Math.round(value));
            } else {
                return new NumberElement(Math.round(value * Math.pow(10, accuracy)) / Math.pow(10, accuracy));
            }
        }));
        global_variables.put("papi", new FunctionElement((Parser parser, SBElement<?>[] inst) ->
                new StringElement(PlaceholderAPI.setPlaceholders(parser.getPlayer().value, "%" + inst[0] + "%"))));
        global_variables.put("pint", new FunctionElement((Parser parser, SBElement<?>[] inst) -> {
            String s = PlaceholderAPI.setPlaceholders(parser.getPlayer().value, "%" + inst[0] + "%");
            if (!NumberUtils.isDigits(s)) return new IntElement(0L);
            return new IntElement(Long.parseLong(s));
        }));
        global_variables.put("pnum", new FunctionElement((Parser parser, SBElement<?>[] inst) -> {
            String s = PlaceholderAPI.setPlaceholders(parser.getPlayer().value, "%" + inst[0] + "%");
            if (!NumberUtils.isNumber(s)) return new NumberElement(0.0);
            return new NumberElement(Double.parseDouble(s));
        }));
        global_variables.put("force_int", new FunctionElement((Parser parser, SBElement<?>[] inst) -> {
            String withOut = inst[0].asString().value.replaceAll("[^\\d-]", "");
            return new IntElement(Long.parseLong(withOut.replaceAll("(?<=\\d)-", "")));
        }));
        global_variables.put("force_number", new FunctionElement((Parser parser, SBElement<?>[] inst) -> {
            StringBuilder result = new StringBuilder();
            String s = inst[0].asString().value;
            boolean hasDecimalPoint = false;
            for (int i = 0; i < s.length(); i++) {
                if ("1234567890.-".contains(s.substring(i, i + 1))) {
                    if (s.charAt(i) == '.') {
                        if (!hasDecimalPoint){
                            hasDecimalPoint = true;
                            if (!result.toString().isEmpty()) result.append('.');
                            else result.append("0.");
                        }
                    } else if (s.charAt(i) == '-') {
                        if (result.toString().isEmpty()) result.append('-');
                    } else result.append(s.charAt(i));
                }
            }
            if (result.toString().endsWith(".")) result.append('0');
            return new NumberElement(Double.parseDouble(result.toString()));
        }));
        global_variables.put("switch", new FunctionElement((Parser parser, SBElement<?>[] inst) -> {
            SBElement<?> element = inst[0];
            for (int i = 1; i < inst.length; i += 2) {
                if (element.equals(inst[i])) return inst[i + 1];
            }
            return inst.length % 2 == 0 ? inst[inst.length - 1] : new VoidElement("switch failed");
        }));
        File custom_global = new File(SBPlaceholder2.plugin.getDataFolder(), "global_variables.yml");
        if (!custom_global.exists())
            SBPlaceholder2.plugin.saveResource("global_variables.yml", false);
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(custom_global);
        for (String key : yml.getKeys(false)) {
            String val = yml.getString(key);
            assert val != null;
            SBElement<?> res = new Parser(val, null, false, -1).parse(null);
            global_variables.put(key, res);
//            String value = yml.getString(key);
//            global_variables.put(key, new FunctionElement((Parser parser, SBElement<?>[] inst) -> {
//                ListElement listElement = new ListElement(inst);
//                parser.getVariables().put("args", listElement);
//                return new ExpressionElement(value).parse(parser);
//            }));
        }
        Bukkit.getPluginManager().callEvent(new GlobalVariablesLoadEvent(global_variables));
    }

    public Parser(String str, @Nullable HashMap<String, SBElement<?>> variables, boolean calculate, int debug) {
        this.debug = debug;
        if (debug >= 0) {
            SBPlaceholder2.logger.info("Parser build: " + str);
            SBPlaceholder2.logger.info("debug level: " + debug);
        }
        this.raw_expression = str;
        if (variables == null) this.variables = new HashMap<>();
        expression = ExpressionType.inst.newInst(raw_expression, true);
        if (debug >= 0) {
            SBPlaceholder2.logger.info("Parser build success in " + (System.currentTimeMillis() - startTime) + "ms");
        }
    }
    public Parser(String str, @Nullable HashMap<String, SBElement<?>> variables, boolean calculate) {
        this(str, variables, calculate, -1);
    }
    public Parser(String str, @Nullable HashMap<String, SBElement<?>> variables) {
        this(str, variables, false, -1);
    }
    public SBElement<?> parse(OfflinePlayer player) {
        long startTime = System.currentTimeMillis();
        if (this.debug >= 0) {
            SBPlaceholder2.logger.info("Parser parse: " + raw_expression);
        }
        variables.put("player", new PlayerElement(player));

        if (expression instanceof ExpressionElement) {
            ExpressionElement expressionInst = (ExpressionElement) expression;
            expression = expressionInst.parse(this);
        }
        if (this.debug >= 0) {
            SBPlaceholder2.logger.info("Parser parse success in " + (System.currentTimeMillis() - startTime) + "ms");
        }
        if (expression instanceof VoidElement) {
            VoidElement voidElement = (VoidElement) expression;
            SBPlaceholder2.logger.warning("表达式 " + raw_expression + " 被拉入虚空!");
            printVoid(voidElement, 0, true);
        }
        return expression;
    }

    private void printVoid(VoidElement voidElement, Integer level, boolean end) {
        StringBuilder prefix = new StringBuilder();
        for (int i = 0; i < level; i++) {
            prefix.append("    ");
        }

        String[] split = voidElement.getCause().split("\n");
        for (int i = 0; i < split.length; i++) {
            if (i == 0)
                if (end)    SBPlaceholder2.logger.warning(prefix + "┗   " + split[i]);
                else        SBPlaceholder2.logger.warning(prefix + "┣   " + split[i]);
            else
                if (end)    SBPlaceholder2.logger.warning(prefix + "    " + split[i]);
                else        SBPlaceholder2.logger.warning(prefix + "┃   " + split[i]);
        }
        ArrayList<VoidElement> relational = voidElement.getRelational();
        for (int i = 0; i < relational.size(); i++) {
            printVoid(relational.get(i), level + 1, i == relational.size() - 1);
        }
    }

    public String getRaw_expression() {
        return raw_expression;
    }

    public SBElement<?> getExpression() {
        return expression;
    }

    public void setExpression(ExpressionElement expression) {
        this.expression = expression;
    }
    public HashMap<String, SBElement<?>> getVariables() {
        return variables;
    }
    public PlayerElement getPlayer() {
        return (PlayerElement) variables.get("player");
    }
    public static HashMap<String, SBElement<?>> getGlobal_variables() {
        return global_variables;
    }

    public long time() {
        return (System.currentTimeMillis() - startTime);
    }
}
