package com.xming.sbplaceholder2.parser.type.type;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBElement;
import com.xming.sbplaceholder2.parser.type.SBType;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
import com.xming.sbplaceholder2.parser.type.inst.*;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class ExpressionType extends SBType<SBElement<?>> {
    public static ExpressionType inst = new ExpressionType();
    private ExpressionType() {}
    public static final HashMap<String, SBElement<?>> cache = new HashMap<>();
    @Override
    public Plugin getPlugin() {
        return SBPlaceholder2.plugin;
    }
    @Override
    public String getName() {
        return "Expression";
}

    @Override
    public String getDescription() {
        return "用于存储表达式。";
    }

    public SBElement<?> newInst(String string, Boolean cache) {
        if (string.isEmpty()) addCache(string, new StringElement(""));
        else if (NumberUtils.isDigits(string)) addCache(string, new IntElement(Integer.parseInt(string)));
        else if (NumberUtils.isNumber(string)) addCache(string, new NumberElement(Float.parseFloat(string)));
        else if (string.equals("void")) addCache(string, VoidElement.instance);
        else if (string.equals("true") || string.equals("false")) addCache(string, BoolElement.fromBool(string.equalsIgnoreCase("true")));

        if (cache) {
            if (!ExpressionType.cache.containsKey(string)) {
                ExpressionElement value = new ExpressionElement(string);
                addCache(string, value);
            }

            try {
                return (SBElement<?>) ExpressionType.cache.get(string).clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return new ExpressionElement(string);
        }
    }

    private void addCache(String string, SBElement<?> value) {
        if (cache.size() > 1000) cache.clear();
        cache.put(string, value);
    }
    @Override
    public SBElement<?> newInst(Parser parser, EntrustInst... insts) {
        return newInst(insts[0].execute(parser).asString().value, true);
    }
}
