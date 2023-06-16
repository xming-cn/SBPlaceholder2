package com.xming.sbplaceholder2.parser;

import com.xming.sbplaceholder2.event.GlobalVariablesLoadEvent;
import com.xming.sbplaceholder2.parser.type.SBElement;
import com.xming.sbplaceholder2.parser.type.inst.*;
import com.xming.sbplaceholder2.parser.type.type.ExpressionType;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class Parser {
    private final String raw_expression;
    private SBElement<?> expression;
    private HashMap<String, SBElement<?>> variables;
    public final int debug;
    public int depth;
    static private HashMap<String, SBElement<?>> global_variables = null;

    public static void loadGlobalVariables() {
        global_variables = new HashMap<>();
        global_variables.put("debug", new FunctionElement((SBElement<?>[] inst) -> new StringElement(inst[0].toDebug())));
        global_variables.put("random", new FunctionElement((SBElement<?>[] inst) -> new NumberElement((float) Math.random())));
        global_variables.put("getAuthor", new FunctionElement((SBElement<?>[] inst) -> new StringElement("xming_jun")));
        global_variables.put("if", new FunctionElement((SBElement<?>[] inst) -> inst[0].asBool().toBool() ? inst[1] : inst[2]));
        global_variables.put("range", new FunctionElement((SBElement<?>[] inst) -> {
            int start = 0, end = 0, step = 1;
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
            for (int i = start; i < end; i += step) listInst.append(new IntElement(i));
            return listInst;
        }));
        global_variables.put("online", new FunctionElement((SBElement<?>[] inst) -> {
            ListElement listInst = new ListElement();
            listInst.addAll(Bukkit.getOnlinePlayers().stream().map(PlayerElement::new).toArray(SBElement[]::new));
            return listInst;
        }));
        Bukkit.getPluginManager().callEvent(new GlobalVariablesLoadEvent(global_variables));
    }

    public Parser(String str, @Nullable HashMap<String, SBElement<?>> variables, int debug) {
        this.raw_expression = str;
        this.debug = debug;
        if (variables == null) this.variables = new HashMap<>();
        expression = ExpressionType.inst.newInst(raw_expression, true);
    }
    public Parser(String str, @Nullable HashMap<String, SBElement<?>> variables) {
        this(str, variables, -1);
    }
    public SBElement<?> parse(OfflinePlayer player) {
        if (global_variables == null) loadGlobalVariables();
        variables.put("player", new PlayerElement(player));

        if (expression instanceof ExpressionElement expressionInst) {
            expression = expressionInst.parse(this);
        }
        return expression.asString();
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
}
