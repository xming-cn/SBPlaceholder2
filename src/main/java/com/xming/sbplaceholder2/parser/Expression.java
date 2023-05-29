package com.xming.sbplaceholder2.parser;

import com.xming.sbplaceholder2.parser.type.SBInst;
import com.xming.sbplaceholder2.parser.type.TypeTool;
import org.bukkit.OfflinePlayer;

import java.util.Arrays;
import java.util.HashMap;

public class Expression implements Cloneable {
    static String[] symbols = {"+", "-", "*", "/", "%", ">=", "<=", ">", "<", "==", "!=", "&&", "||", "!"};
    static HashMap<String, Expression> cache = new HashMap<>();

    int max_length = 4;
    SBInst<?>[] object = new SBInst[max_length + 1];
    String[] operator = new String[max_length];

    static public Expression getExpression(OfflinePlayer player, String expression) {
        if (cache.containsKey(expression)) return cache.get(expression);
        Expression new_expression = new Expression(player, expression);
        cache.put(expression, new_expression);
        return new_expression;
    }
    public Expression() {}
    private Expression(OfflinePlayer player, String expression) {
        int object_count = 0;
        int start_pos = 0;
        boolean in_string = false;
        boolean in_bracket = false;
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '\'' || expression.charAt(i) == '\"') in_string = !in_string;
            if (in_string) continue;
            if (expression.charAt(i) == '(' || expression.charAt(i) == ')') in_bracket = !in_bracket;
            if (in_bracket) continue;
            for (String symbol : symbols) {
                if (i + symbol.length() > expression.length()) continue;
                if (expression.subSequence(i, i + symbol.length()).equals(symbol)) {
                    object_count += 1;
                    if (object_count > max_length) {
                        max_length *= 2;
                        this.operator = Arrays.copyOf(this.operator, this.operator.length * 2);
                        this.object = Arrays.copyOf(this.object, this.object.length * 2 + 1);
                    }

                    this.operator[object_count - 1] = symbol;
                    this.object[object_count - 1] = TypeTool.parse(player, expression.substring(start_pos, i));
                    start_pos = i + symbol.length();
                }
            }
        }
        this.object[object_count] = TypeTool.parse(player, expression.substring(start_pos));
    }
    @Override
    public Expression clone() {
        Expression expression = new Expression();
        expression.max_length = this.max_length;
        expression.object = Arrays.copyOf(this.object, this.max_length + 1);
        expression.operator = Arrays.copyOf(this.operator, this.max_length);
        return expression;
    }
}
