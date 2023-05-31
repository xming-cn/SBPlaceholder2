package com.xming.sbplaceholder2.parser.type.inst;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBInst;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustTool;
import com.xming.sbplaceholder2.parser.type.type.ExpressionType;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.HashMap;

public class ExpressionInst extends SBInst<ExpressionType> {
    static final String[] symbols = {"+", "-", "*", "/", "%", ">=", "<=", ">", "<", "==", "!=", "&&", "||", "!"};
    static HashMap<String, ExpressionInst> cache = new HashMap<>();
    int max_length = 4;
    EntrustInst[] object = new EntrustInst[max_length + 1];
    String[] operator = new String[max_length];

    public ExpressionInst(Parser parser, String rawExpression) {
        int object_count = 0;
        int start_pos = 0;
        boolean in_string = false;
        boolean in_bracket = false;
        for (int i = 0; i < rawExpression.length(); i++) {
            if (rawExpression.charAt(i) == '\'' ||
                    rawExpression.charAt(i) == '\"') in_string = !in_string;
            if (in_string) continue;
            if (rawExpression.charAt(i) == '(' ||
                    rawExpression.charAt(i) == ')') in_bracket = !in_bracket;
            if (in_bracket) continue;
            for (String symbol : symbols) {
                if (i + symbol.length() > rawExpression.length()) continue;
                if (rawExpression.subSequence(i, i + symbol.length()).equals(symbol)) {
                    object_count += 1;
                    if (object_count > max_length) {
                        max_length *= 2;
                        this.operator = Arrays.copyOf(this.operator, this.operator.length * 2);
                        this.object = Arrays.copyOf(this.object, this.object.length * 2 + 1);
                    }

                    this.operator[object_count - 1] = symbol;
                    this.object[object_count - 1] = EntrustTool.parse(parser, rawExpression.substring(start_pos, i));
                    start_pos = i + symbol.length();
                }
            }
        }
        this.object[object_count] = EntrustTool.parse(parser, rawExpression.substring(start_pos));
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
        return "ExpressionInst{" +
                "object=" + Arrays.toString(object) +
                ", operator=" + Arrays.toString(operator) +
                '}';
    }
}
