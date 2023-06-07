package com.xming.sbplaceholder2.parser.type.inst;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.common.ArrayUtils;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBInst;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustTool;
import com.xming.sbplaceholder2.parser.type.type.ExpressionType;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

public class ExpressionInst extends SBInst<ExpressionType> implements Cloneable {
    static final String[] symbols = {"+", "-", "*", "/", "%", ">=", "<=", ">", "<", "==", "!=", "&&", "||", "!"};
    int max_length = 4;
    public EntrustInst[] entrust = new EntrustInst[max_length + 1];
    public String[] operator = new String[max_length];

    private ExpressionInst() {
    }
    public ExpressionInst(String rawExpression) {
        int object_count = 0;
        int start_pos = 0;
        boolean in_string = false;
        int in_bracket = 0;
        for (int i = 0; i < rawExpression.length(); i++) {
            if (rawExpression.charAt(i) == '\'' ||
                    rawExpression.charAt(i) == '\"') in_string = !in_string;
            if (in_string) continue;
            if (rawExpression.charAt(i) == '(') in_bracket += 1;
            else if (rawExpression.charAt(i) == ')') in_bracket -= 1;
            if (in_bracket > 0) continue;
            for (String symbol : symbols) {
                if (i + symbol.length() > rawExpression.length()) continue;
                if (rawExpression.subSequence(i, i + symbol.length()).equals(symbol)) {
                    object_count += 1;
                    if (object_count > max_length) {
                        max_length *= 2;
                        this.operator = Arrays.copyOf(this.operator, this.operator.length * 2);
                        this.entrust = Arrays.copyOf(this.entrust, this.entrust.length * 2 + 1);
                    }

                    this.operator[object_count - 1] = symbol;
                    this.entrust[object_count - 1] = EntrustTool.parse(rawExpression.substring(start_pos, i));
                    start_pos = i + symbol.length();
                }
            }
        }
        this.entrust[object_count] = EntrustTool.parse(rawExpression.substring(start_pos));
    }
    @Override
    public Plugin getPlugin() {
        return SBPlaceholder2.plugin;
    }
    @Override
    public String getName() {
        return "Expression";
    }
    @Override
    public String toString() {
        return ArrayUtils.toString(entrust) + "~" + ArrayUtils.toString(operator);
    }
    public SBInst<?>[] execute(Parser parser, OfflinePlayer player) {
        SBInst<?>[] result = new SBInst<?>[entrust.length];
        for (int i = 0; i < entrust.length; i++) {
            if (entrust[i] != null)
                result[i] = entrust[i].execute(parser, player);
        }
        return result;
    }
    public SBInst<?> parse(Parser parser, OfflinePlayer player) {
        SBInst<?>[] object = execute(parser, player);
        while (true) {
            int max_priority = -1;
            int max_priority_pos = -1;
            for (int i = 0; i < operator.length; i++) {
                if (operator[i] == null) continue;
                int priority = getPriority(operator[i]);
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

            String symbol = operator[max_priority_pos];
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
                case "**" -> {
                    object[this_object_pos] = object[this_object_pos].symbol_double_mul(other_object);
                    object[other_object_pos] = null;
                }
                case "//" -> {
                    object[this_object_pos] = object[this_object_pos].symbol_double_div(other_object);
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
            operator[max_priority_pos] = null;
        }
        return object[0];
    }
    private int getPriority(String s) {
        return switch (s) {
            case "**" -> 7;
            case "*", "/", "%", "//" -> 6;
            case "+", "-" -> 5;
            case ">", "<", ">=", "<=" -> 4;
            case "==", "!=" -> 3;
            case "&&" -> 2;
            case "||" -> 1;
            case "!" -> 0;
            default -> -1;
        };
    }
    @Override
    public ExpressionInst clone() {
        ExpressionInst inst = new ExpressionInst();
        inst.max_length = max_length;
        inst.entrust = new EntrustInst[max_length + 1];
        for (int i = 0; i < entrust.length; i++) {
            if (entrust[i] != null)
                inst.entrust[i] = (EntrustInst) entrust[i].clone();
        }
        inst.operator = new String[max_length];
        System.arraycopy(operator, 0, inst.operator, 0, operator.length);
        return inst;
    }
}
