package com.xming.sbplaceholder2.parser;

import com.xming.sbplaceholder2.parser.type.SBInst;
import com.xming.sbplaceholder2.parser.type.inst.PlayerInst;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class Parser {
    private String raw_expression;
    private Expression expression;
    private HashMap<String, SBInst<?>> variables;

    public Parser(String str, @Nullable HashMap<String, SBInst<?>> variables) {
        this.raw_expression = str;
        if (variables == null) variables = new HashMap<>();
        expression = Expression.getExpression(this);
    }
    public SBInst<?> parse(OfflinePlayer player) {
        variables.put("player", new PlayerInst(player));
        SBInst<?>[] object = expression.execute();
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
            while (object[this_object_pos] == null) this_object_pos -= 1;
            int other_object_pos = this_object_pos + 1;
            while (object[other_object_pos] == null) other_object_pos += 1;
            SBInst<?> other_object = object[other_object_pos];

            String symbol = expression.operator[max_priority_pos];
            switch (symbol) {
                case "+" -> {
                    object[this_object_pos] = object[this_object_pos].symbol_add(other_object);
                    object[other_object_pos] = null;
                }
                case "-" -> {
                    object[this_object_pos] = object[this_object_pos].symbol_sub(other_object);
                    object[other_object_pos] = null;
                }
                case "*" -> {
                    object[this_object_pos] = object[this_object_pos].symbol_mul(other_object);
                    object[other_object_pos] = null;
                }
                case "/" -> {
                    object[this_object_pos] = object[this_object_pos].symbol_div(other_object);
                    object[other_object_pos] = null;
                }
                case "%" -> {
                    object[this_object_pos] = object[this_object_pos].symbol_mod(other_object);
                    object[other_object_pos] = null;
                }
                case ">" -> {
                    object[this_object_pos] = object[this_object_pos].symbol_greater(other_object);
                    object[other_object_pos] = null;
                }
                case "<" -> {
                    object[this_object_pos] = object[this_object_pos].symbol_less(other_object);
                    object[other_object_pos] = null;
                }
                case ">=" -> {
                    object[this_object_pos] = object[this_object_pos].symbol_egreater(other_object);
                    object[other_object_pos] = null;
                }
                case "<=" -> {
                    object[this_object_pos] = object[this_object_pos].symbol_eless(other_object);
                    object[other_object_pos] = null;
                }
                case "==" -> {
                    object[this_object_pos] = object[this_object_pos].symbol_equal(other_object);
                    object[other_object_pos] = null;
                }
                case "!=" -> {
                    object[this_object_pos] = object[this_object_pos].symbol_not_equal(other_object);
                    object[other_object_pos] = null;
                }
                case "&&" -> {
                    object[this_object_pos] = object[this_object_pos].symbol_and(other_object);
                    object[other_object_pos] = null;
                }
                case "||" -> {
                    object[this_object_pos] = object[this_object_pos].symbol_or(other_object);
                    object[other_object_pos] = null;
                }
                case "!" -> object[this_object_pos] = object[this_object_pos].symbol_not();
            }
            expression.operator[max_priority_pos] = null;
        }
        return object[0];
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

    public String getRaw_expression() {
        return raw_expression;
    }

    public void setRaw_expression(String raw_expression) {
        this.raw_expression = raw_expression;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public HashMap<String, SBInst<?>> getVariables() {
        return variables;
    }

    public void setVariables(HashMap<String, SBInst<?>> variables) {
        this.variables = variables;
    }
}
