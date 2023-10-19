package com.xming.sbplaceholder2.parser.type.entrust;

import com.xming.sbplaceholder2.parser.type.SBElement;
import com.xming.sbplaceholder2.parser.type.inst.*;
import com.xming.sbplaceholder2.parser.type.type.ExpressionType;
import org.apache.commons.lang.math.NumberUtils;

import java.util.ArrayList;

public class EntrustTool {
    public static EntrustInst parse(String str) {
        String trim = str.trim();
        ArrayList<String> actions = splitBy(trim, '.');
        EntrustInst entrust = new EntrustInst();
        SBElement<?> object = getInstFromString(entrust, actions.remove(0));
        entrust.setObject(object);
        if (actions.size() > 0) {
            if (object instanceof IntElement intInst) {
                String digits = actions.get(0);
                if (NumberUtils.isDigits(digits)) {
                    entrust.setObject(new NumberElement(intInst.value + NumberUtils.toDouble("0." + digits)));
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
                for (int i = 0; i < strArgs.length; i++) {
                    SBElement<?> inst = ExpressionType.inst.newInst(strArgs[i], false);
                    if (inst instanceof ExpressionElement)
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
    public static SBElement<?> getInstFromString(EntrustInst entrust, String str) {
        if (str.startsWith("(") && str.endsWith(")")) {
            String substring = str.substring(1, str.length() - 1);
            SBElement<?> sbElement = ExpressionType.inst.newInst(substring, false);
            if (sbElement instanceof ExpressionElement) {
                entrust.addTask(new Task(Task.TaskType.SUB_EXPRESSION, null));
            }
            return sbElement;
        } else if (str.startsWith("`") && str.endsWith("`")) {
            return new StringElement(str.substring(1, str.length() - 1));
        }  else if (str.startsWith("\"") && str.endsWith("\"")) {
            return new StringElement(str.substring(1, str.length() - 1));
        } else if (str.startsWith("'") && str.endsWith("'")) {
            return new StringElement(str.substring(1, str.length() - 1));
        } else if (NumberUtils.isDigits(str)) {
            return new IntElement(NumberUtils.toInt(str));
        } else if (NumberUtils.isNumber(str)) {
            System.out.println(str + " is a number but not a digit");
            return new NumberElement(NumberUtils.toDouble(str));
        } else if (str.equals("void")) {
            return VoidElement.instance;
        } else if (str.equals("true")) {
            return BoolElement.trueInstance;
        } else if (str.equals("false")) {
            return BoolElement.falseInstance;
        } else if (str.endsWith(")")) {
            int start = findLast(str, '(');
            if (start != -1) {
                String name = str.substring(0, start);
                String[] strArgs = splitBy(str.substring(start + 1, str.length() - 1), ',').toArray(new String[0]);
                EntrustInst[] args = new EntrustInst[strArgs.length];
                for (int i = 0; i < strArgs.length; i++) {
                    SBElement<?> inst = ExpressionType.inst.newInst(strArgs[i], false);
                    if (inst instanceof ExpressionElement)
                        args[i] = new EntrustInst(inst, new Task(Task.TaskType.SUB_EXPRESSION, null));
                    else args[i] = new EntrustInst(inst);
                }
                SBElement<?> instFromString = getInstFromString(entrust, name);
                entrust.addTask(new Task(Task.TaskType.CALL_SELF, null, args));
                return instFromString;
            } else {
                entrust.addTask(new Task(Task.TaskType.PARSE_VARIABLE, null));
                return new StringElement(str);
            }
        } else {
            entrust.addTask(new Task(Task.TaskType.PARSE_VARIABLE, null));
            return new StringElement(str);
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
                else if (c == '`') state = '`';

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
    public static int findLast(String str, Character t) {
        Character state = null;
        int depth = 0;
        for (int i = str.length() - 1; i >= 0; i--) {
            char c = str.charAt(i);
            if (state == null) {
                if (c == ')') depth++;
                else if (c == '(') depth--;
                if (depth > 0) continue;

                // three way to fast build a string
                if (c == '"') state = '"';
                else if (c == '\'') state = '\'';
                else if (c == '`') state = '`';


                if (c == t) {
                    return i;
                }
            } else {
                if (c == state) state = null;
            }
        }
        return -1;
    }
}
