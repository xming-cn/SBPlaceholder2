package com.xming.sbplaceholder2.parser.type.entrust;

import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBInst;
import com.xming.sbplaceholder2.parser.type.inst.*;
import org.apache.commons.lang.math.NumberUtils;

import java.util.ArrayList;

public class EntrustTool {
    public static EntrustInst parse(Parser parser, String str) {
        String trim = str.trim();
        ArrayList<String> actions = splitBy(trim, '.');
        SBInst<?> object = getInstFromString(parser, actions.remove(0));
        if (actions.size() > 0) {
            if (object instanceof IntInst intInst) {
                String digits = actions.get(0);
                if (NumberUtils.isDigits(digits)) {
                    object = new NumberInst(intInst.value + NumberUtils.toFloat("0." + digits));
                    actions.remove(0);
                }
            }
        }

        EntrustInst entrust = new EntrustInst(object);
        if (actions.size() == 0) {
            return entrust;
        }

        for (String action : actions) {
            if (action.matches(".+\\(.*\\)")) {
                int start = action.indexOf('(');
                int end = action.lastIndexOf(')');
                String name = action.substring(0, start);
                String[] strArgs = splitBy(action.substring(start + 1, end), ',').toArray(new String[0]);
                SBInst<?>[] args = new SBInst<?>[strArgs.length];
                for (int i = 0; i < strArgs.length; i++) {
                    args[i] = getInstFromString(parser, strArgs[i]);
                }
                entrust.addTask(new Task(Task.TaskType.CALL_METHOD, name, args));
            } else {
                entrust.addTask(new Task(Task.TaskType.GET_FIELD, action));
            }
        }
        return entrust;
    }
    public static SBInst<?> getInstFromString(Parser parser, String str) {
        if (str.startsWith("(") && str.endsWith(")")) {
            String substring = str.substring(1, str.length() - 1);
            return new ExpressionInst(parser, substring);
        } else if (str.startsWith("\"") && str.endsWith("\"")) {
            return new StringInst(str.substring(1, str.length() - 1));
        } else if (str.startsWith("'") && str.endsWith("'")) {
            return new StringInst(str.substring(1, str.length() - 1));
        } else if (NumberUtils.isDigits(str)) {
            return new IntInst(NumberUtils.toInt(str));
        } else if (NumberUtils.isNumber(str)) {
            return new NumberInst(NumberUtils.toFloat(str));
        }  else if (str.equalsIgnoreCase("true")) {
            return new BoolInst(true);
        } else if (str.equalsIgnoreCase("false")) {
            return new BoolInst(false);
        } else {
            if (parser.getVariables() != null && parser.getVariables().containsKey(str))
                return parser.getVariables().get(str);
            return new StringInst(str);
        }
    }
    public static ArrayList<String> splitBy(String str, Character separator) {
        Character state = null;
        ArrayList<String> result = new ArrayList<>();
        StringBuilder this_object = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (state == null) {
                if (c == '(') state = ')';
                else if (c == '"') state = '"';
                else if (c == '\'') state = '\'';

                if (c == separator) {
                    result.add(this_object.toString());
                    this_object = new StringBuilder();
                    continue;
                }
            } else {
                if (c == state) state = null;
            }
            this_object.append(c);
        }
        result.add(this_object.toString());
        return result;
    }
}
