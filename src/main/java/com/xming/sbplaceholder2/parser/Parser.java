package com.xming.sbplaceholder2.parser;

import com.xming.sbplaceholder2.parser.type.SBInst;
import org.bukkit.OfflinePlayer;

public class Parser {
    String raw_expression;
    Expression expression;
    public Parser(String str) {
        this.raw_expression = str;
    }
    public SBInst<?> parse(OfflinePlayer player) {
        expression = Expression.getExpression(player, raw_expression);
        while (true) {
            int max_priority = -1;
            int max_priority_pos = -1;
            for (int i = 0; i < expression.operator.length; i++) {
                if (expression.operator[i] == null) continue;
                int priority = getPriority(expression.operator[i]);
                if (priority > max_priority) {
                    max_priority = priority;
                    max_priority_pos = i;
                }
            }

            if (max_priority_pos < 0) break;
            int this_object_pos = max_priority_pos;
            while (expression.object[this_object_pos] == null) this_object_pos -= 1;
            int other_object_pos = this_object_pos + 1;
            while (expression.object[other_object_pos] == null) other_object_pos += 1;
            SBInst<?> other_object = expression.object[other_object_pos];

            String symbol = expression.operator[max_priority_pos];
            switch (symbol) {
                case "+" -> {
                    expression.object[this_object_pos] = expression.object[this_object_pos].symbol_add(other_object);
                    expression.object[other_object_pos] = null;
                }
                case "-" -> {
                    expression.object[this_object_pos] = expression.object[this_object_pos].symbol_sub(other_object);
                    expression.object[other_object_pos] = null;
                }
                case "*" -> {
                    expression.object[this_object_pos] = expression.object[this_object_pos].symbol_mul(other_object);
                    expression.object[other_object_pos] = null;
                }
                case "/" -> {
                    expression.object[this_object_pos] = expression.object[this_object_pos].symbol_div(other_object);
                    expression.object[other_object_pos] = null;
                }
                case ">" -> {
                    expression.object[this_object_pos] = expression.object[this_object_pos].symbol_greater(other_object);
                    expression.object[other_object_pos] = null;
                }
                case "<" -> {
                    expression.object[this_object_pos] = expression.object[this_object_pos].symbol_less(other_object);
                    expression.object[other_object_pos] = null;
                }
                case ">=" -> {
                    expression.object[this_object_pos] = expression.object[this_object_pos].symbol_egreater(other_object);
                    expression.object[other_object_pos] = null;
                }
                case "<=" -> {
                    expression.object[this_object_pos] = expression.object[this_object_pos].symbol_eless(other_object);
                    expression.object[other_object_pos] = null;
                }
                case "==" -> {
                    expression.object[this_object_pos] = expression.object[this_object_pos].symbol_equal(other_object);
                    expression.object[other_object_pos] = null;
                }
                case "!=" -> {
                    expression.object[this_object_pos] = expression.object[this_object_pos].symbol_not_equal(other_object);
                    expression.object[other_object_pos] = null;
                }
                case "&&" -> {
                    expression.object[this_object_pos] = expression.object[this_object_pos].symbol_and(other_object);
                    expression.object[other_object_pos] = null;
                }
                case "||" -> {
                    expression.object[this_object_pos] = expression.object[this_object_pos].symbol_or(other_object);
                    expression.object[other_object_pos] = null;
                }
                case "!" -> {
                    expression.object[this_object_pos] = expression.object[this_object_pos].symbol_not();
                }
            }
            expression.operator[max_priority_pos] = null;
        }
        return expression.object[0];
    }
    private int getPriority(String s) {
        return switch (s) {
            case "*", "/", "%" -> 6;
            case "+", "-" -> 5;
            case ">", "<", ">=", "<=" -> 4;
            case "==", "!=" -> 3;
            case "&&" -> 2;
            case "||" -> 1;
            case "!" -> 0;
            default -> -1;
        };
    }
}
