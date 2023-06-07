package com.xming.sbplaceholder2.parser;

import com.xming.sbplaceholder2.parser.type.SBInst;
import com.xming.sbplaceholder2.parser.type.inst.*;
import com.xming.sbplaceholder2.parser.type.type.ExpressionType;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class Parser {
    private final String raw_expression;
    private SBInst<?> expression;
    private HashMap<String, SBInst<?>> variables;
    public final int debug;
    public int depth;

    public Parser(String str, @Nullable HashMap<String, SBInst<?>> variables, int debug) {
        this.raw_expression = str;
        this.debug = debug;
        if (variables == null) this.variables = new HashMap<>();
        expression = ExpressionType.inst.newInst(raw_expression);
    }
    public Parser(String str, @Nullable HashMap<String, SBInst<?>> variables) {
        this(str, variables, -1);
    }
    public SBInst<?> parse(OfflinePlayer player) {
        variables.put("player", new PlayerInst(player));
        variables.put("debug", new FunctionInst((SBInst<?>[] inst) -> new StringInst(inst[0].toDebug())));
        variables.put("random", new FunctionInst((SBInst<?>[] inst) -> new NumberInst((float) Math.random())));
        variables.put("getAuthor", new FunctionInst((SBInst<?>[] inst) -> new StringInst("xming_jun")));
        variables.put("if", new FunctionInst((SBInst<?>[] inst) -> inst[0].asBool().toBool() ? inst[1] : inst[2]));
        variables.put("range", new FunctionInst((SBInst<?>[] inst) -> {
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
            ListInst listInst = new ListInst();
            for (int i = start; i < end; i += step) listInst.append(new IntInst(i));
            return listInst;
        }));
        variables.put("online", new FunctionInst((SBInst<?>[] inst) -> {
            ListInst listInst = new ListInst();
            listInst.addAll(Bukkit.getOnlinePlayers().stream().map(PlayerInst::new).toArray(SBInst[]::new));
            return listInst;
        }));

        if (expression instanceof ExpressionInst expressionInst) {
            expression = expressionInst.parse(this, player);
        }
        return expression.asString();
    }

    public String getRaw_expression() {
        return raw_expression;
    }

    public SBInst<?> getExpression() {
        return expression;
    }

    public void setExpression(ExpressionInst expression) {
        this.expression = expression;
    }
    public HashMap<String, SBInst<?>> getVariables() {
        return variables;
    }
    public PlayerInst getPlayer() {
        return (PlayerInst) variables.get("player");
    }
}
