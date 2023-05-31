package com.xming.sbplaceholder2.parser;

import com.xming.sbplaceholder2.parser.type.SBInst;
import com.xming.sbplaceholder2.parser.type.SBType;
import com.xming.sbplaceholder2.parser.type.TypeManager;
import com.xming.sbplaceholder2.parser.type.inst.ExpressionInst;
import com.xming.sbplaceholder2.parser.type.inst.FunctionInst;
import com.xming.sbplaceholder2.parser.type.inst.PlayerInst;
import com.xming.sbplaceholder2.parser.type.inst.StringInst;
import com.xming.sbplaceholder2.parser.type.type.ExpressionType;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class Parser {
    private String raw_expression;
    private ExpressionInst expression;
    private HashMap<String, SBInst<?>> variables;

    public Parser(String str, @Nullable HashMap<String, SBInst<?>> variables) {
        this.raw_expression = str;
        if (variables == null) this.variables = new HashMap<>();
        SBType<?> expr = TypeManager.getInstance().getType("Expression");
        expression = ((ExpressionType) expr).newInst(raw_expression);
    }
    public SBInst<?> parse(OfflinePlayer player) {
        variables.put("player", new PlayerInst(player));
        variables.put("debug", new FunctionInst((SBInst<?>[] inst) -> new StringInst(inst[0].toDebug())));

        return expression.parse(this, player);
    }

    public String getRaw_expression() {
        return raw_expression;
    }

    public void setRaw_expression(String raw_expression) {
        this.raw_expression = raw_expression;
    }

    public ExpressionInst getExpression() {
        return expression;
    }

    public void setExpression(ExpressionInst expression) {
        this.expression = expression;
    }

    public HashMap<String, SBInst<?>> getVariables() {
        return variables;
    }

    public void setVariables(HashMap<String, SBInst<?>> variables) {
        this.variables = variables;
    }
}
