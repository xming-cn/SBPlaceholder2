package com.xming.sbplaceholder2.parser.type.type;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.type.SBInst;
import com.xming.sbplaceholder2.parser.type.SBType;
import com.xming.sbplaceholder2.parser.type.inst.*;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class ExpressionType extends SBType<SBInst<?>> {
    public static ExpressionType inst = new ExpressionType();
    private ExpressionType() {}
    public static final HashMap<String, SBInst<?>> cache = new HashMap<>();
    @Override
    public Plugin getPlugin() {
        return SBPlaceholder2.plugin;
    }
    @Override
    public String getName() {
        return "Expression";
}
    public SBInst<?> newInst(String string) {
//        while (string.startsWith("(") && string.endsWith(")")) {
//            string = string.substring(1, string.length() - 1);
//        }

        if (string.isEmpty()) cache.put(string, new StringInst(""));
        else if (NumberUtils.isDigits(string)) cache.put(string, new IntInst(Integer.parseInt(string)));
        else if (NumberUtils.isNumber(string)) cache.put(string, new NumberInst(Float.parseFloat(string)));
        else if (string.equals("void")) cache.put(string, VoidInst.instance);
        else if (string.equals("true") || string.equals("false")) cache.put(string, BoolInst.fromBool(string.equalsIgnoreCase("true")));

        if (!cache.containsKey(string)) {
            ExpressionInst value = new ExpressionInst(string);
            cache.put(string, value);
        }

        try {
            return (SBInst<?>) cache.get(string).clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public SBInst<?> newInst(SBInst<?>... insts) {
        String string = insts[0].asString().value;
        return newInst(string);
    }
}
