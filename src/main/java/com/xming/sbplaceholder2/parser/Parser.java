package com.xming.sbplaceholder2.parser;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.event.GlobalVariablesLoadEvent;
import com.xming.sbplaceholder2.parser.type.SBElement;
import com.xming.sbplaceholder2.parser.type.inst.*;
import com.xming.sbplaceholder2.parser.type.type.ExpressionType;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class Parser {
    private final String raw_expression;
    private SBElement<?> expression;
    private HashMap<String, SBElement<?>> variables;
    public final int debug;
    public int depth;
    static private HashMap<String, SBElement<?>> global_variables = null;

    private final long startTime = System.currentTimeMillis();

    public static void loadGlobalVariables() {
        global_variables = new HashMap<>();
        global_variables.put("debug", new FunctionElement((SBElement<?>[] inst) -> new StringElement(inst[0].toDebug())));
        global_variables.put("random", new FunctionElement((SBElement<?>[] inst) -> new NumberElement(Math.random())));
        global_variables.put("getAuthor", new FunctionElement((SBElement<?>[] inst) -> new StringElement("xming_jun")));
        global_variables.put("if", new FunctionElement((SBElement<?>[] inst) -> inst[0].asBool().toBool() ? inst[1] : inst[2]));
        global_variables.put("range", new FunctionElement((SBElement<?>[] inst) -> {
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
        global_variables.put("online", new FunctionElement((SBElement<?>[] inst) -> {
            ListElement listInst = new ListElement();
            listInst.addAll(Bukkit.getOnlinePlayers().stream().map(PlayerElement::new).toArray(SBElement[]::new));
            return listInst;
        }));
        global_variables.put("round", new FunctionElement((SBElement<?>[] inst) -> {
            long accuracy = inst.length > 1 ? inst[1].asInt().value : 0;
            double value = inst[0].asNumber().value;
            if (accuracy == 0) {
                return new IntElement(Math.round(value));
            } else {
                return new NumberElement(Math.round(value * Math.pow(10, accuracy)) / Math.pow(10, accuracy));
            }
        }));
        Bukkit.getPluginManager().callEvent(new GlobalVariablesLoadEvent(global_variables));
    }

    public Parser(String str, @Nullable HashMap<String, SBElement<?>> variables, int debug) {
        this.debug = debug;
        if (debug >= 0) {
            SBPlaceholder2.logger.info("Parser build: " + str);
            SBPlaceholder2.logger.info("debug level: " + debug);
        }
        this.raw_expression = str;
        if (variables == null) this.variables = new HashMap<>();
        this.variables.put("papi", new FunctionElement(
                (SBElement<?>[] inst) -> new StringElement(PlaceholderAPI.setPlaceholders(this.getPlayer().value, "%" + inst[0] + "%"))
        ));
        expression = ExpressionType.inst.newInst(raw_expression, true);
        if (debug >= 0) {
            SBPlaceholder2.logger.info("Parser build success in " + (System.currentTimeMillis() - startTime) + "ms");
        }
    }
    public Parser(String str, @Nullable HashMap<String, SBElement<?>> variables) {
        this(str, variables, -1);
    }
    public SBElement<?> parse(OfflinePlayer player) {
        long startTime = System.currentTimeMillis();
        if (this.debug >= 0) {
            SBPlaceholder2.logger.info("Parser parse: " + raw_expression);
        }
        if (global_variables == null) loadGlobalVariables();
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
        return expression.asString();
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
