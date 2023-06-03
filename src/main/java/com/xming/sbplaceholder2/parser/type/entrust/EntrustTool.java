package com.xming.sbplaceholder2.parser.type.entrust;

import com.xming.sbplaceholder2.parser.type.SBInst;
import com.xming.sbplaceholder2.parser.type.SBType;
import com.xming.sbplaceholder2.parser.type.TypeManager;
import com.xming.sbplaceholder2.parser.type.inst.*;
import com.xming.sbplaceholder2.parser.type.type.ExpressionType;
import org.apache.commons.lang.math.NumberUtils;

import java.util.ArrayList;

public class EntrustTool {
    public static EntrustInst parse(String str) {
        String trim = str.trim();
        ArrayList<String> actions = splitBy(trim, '.');
        EntrustInst entrust = new EntrustInst();
        SBInst<?> object = getInstFromString(entrust, actions.remove(0));
        entrust.setObject(object);
        if (actions.size() > 0) {
            if (object instanceof IntInst intInst) {
                String digits = actions.get(0);
                if (NumberUtils.isDigits(digits)) {
                    entrust.setObject(new NumberInst(intInst.value + NumberUtils.toFloat("0." + digits)));
                    actions.remove(0);
                }
            }
        }

        if (actions.size() == 0) {
            return entrust;
        }

        for (String action : actions) {
            if (action.matches(".+\\(.*\\)")) {
                int start = action.indexOf('(');
                int end = action.lastIndexOf(')');
                String name = action.substring(0, start);
                String[] strArgs = splitBy(action.substring(start + 1, end), ',').toArray(new String[0]);
                EntrustInst[] args = new EntrustInst[strArgs.length];
                ExpressionType expr = (ExpressionType) TypeManager.getInstance().getType("Expression");
                for (int i = 0; i < strArgs.length; i++) {
                    SBInst<?> inst = expr.newInst(strArgs[i]);
                    if (inst instanceof ExpressionInst)
                        args[i] = new EntrustInst(inst, new Task(Task.TaskType.SUB_EXPRESSION, null));
                    else args[i] = new EntrustInst(inst);
                }
                entrust.addTask(new Task(Task.TaskType.CALL_METHOD, name, args));
            } else {
                entrust.addTask(new Task(Task.TaskType.GET_FIELD, action));
            }
        }
        return entrust;
    }
    public static SBInst<?> getInstFromString(EntrustInst entrust, String str) {
        if (str.startsWith("(") && str.endsWith(")")) {
            String substring = str.substring(1, str.length() - 1);
            SBType<?> expr = TypeManager.getInstance().getType("Expression");
            SBInst<?> sbInst = ((ExpressionType) expr).newInst(substring);
            if (sbInst instanceof ExpressionInst) {
                entrust.addTask(new Task(Task.TaskType.SUB_EXPRESSION, null));
            }
            return sbInst;
        } else if (str.startsWith("\"") && str.endsWith("\"")) {
            return new StringInst(str.substring(1, str.length() - 1));
        } else if (str.startsWith("'") && str.endsWith("'")) {
            return new StringInst(str.substring(1, str.length() - 1));
        } else if (NumberUtils.isDigits(str)) {
            return new IntInst(NumberUtils.toInt(str));
        } else if (NumberUtils.isNumber(str)) {
            System.out.println(str + " is a number but not a digit");
            return new NumberInst(NumberUtils.toFloat(str));
        } else if (str.equals("void")) {
            return VoidInst.instance;
        } else if (str.equals("true")) {
            return BoolInst.trueInstance;
        } else if (str.equals("false")) {
            return BoolInst.falseInstance;
        } else if (str.endsWith(")")) {
            int start = str.indexOf('(');
            if (start != -1) {
                String name = str.substring(0, start);
                String[] strArgs = splitBy(str.substring(start + 1, str.length() - 1), ',').toArray(new String[0]);
                EntrustInst[] args = new EntrustInst[strArgs.length];
                ExpressionType expr = (ExpressionType) TypeManager.getInstance().getType("Expression");
                for (int i = 0; i < strArgs.length; i++) {
                    SBInst<?> inst = expr.newInst(strArgs[i]);
                    if (inst instanceof ExpressionInst)
                        args[i] = new EntrustInst(inst, new Task(Task.TaskType.SUB_EXPRESSION, null));
                    else args[i] = new EntrustInst(inst);
                }
                SBInst<?> instFromString = getInstFromString(entrust, name);
                entrust.addTask(new Task(Task.TaskType.CALL_SELF, null, args));
                return instFromString;
            } else {
                entrust.addTask(new Task(Task.TaskType.PARSE_VARIABLE, null));
                return new StringInst(str);
            }
        } else {
            entrust.addTask(new Task(Task.TaskType.PARSE_VARIABLE, null));
            return new StringInst(str);
        }
    }
    public static ArrayList<String> splitBy(String str, Character separator) {
        Character state = null;
        int depth = 0;
        ArrayList<String> result = new ArrayList<>();
        StringBuilder this_object = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (state == null) {
                if (c == '(') depth++;
                else if (c == ')') depth--;
                if (depth > 0) {
                    this_object.append(c);
                    continue;
                }

                if (c == '"') state = '"';
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
