package com.xming.sbplaceholder2.parser.type.type;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBElement;
import com.xming.sbplaceholder2.parser.type.SBType;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
import com.xming.sbplaceholder2.parser.type.inst.BoolElement;
import com.xming.sbplaceholder2.parser.type.inst.ExpressionElement;
import com.xming.sbplaceholder2.parser.type.inst.StringElement;
import com.xming.sbplaceholder2.parser.type.inst.VoidElement;
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
        if (ExpressionType.cache.isEmpty()) {
            addCache("true", BoolElement.trueInstance);
            addCache("false", BoolElement.falseInstance);
            addCache("void", VoidElement.instance);
            addCache("", new StringElement(""));
        }
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
