package com.xming.sbplaceholder2.parser.type.type;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.type.SBType;
import com.xming.sbplaceholder2.parser.type.inst.ExpressionInst;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class ExpressionType extends SBType<ExpressionInst> {
    static final HashMap<String, ExpressionInst> cache = new HashMap<>();
    @Override
    public Plugin getPlugin() {
        return SBPlaceholder2.plugin;
    }
    @Override
    public String getName() {
        return "Expression";
    }
    @Override
    public ExpressionInst newInst(String str) {
        if (!cache.containsKey(str)) cache.put(str, new ExpressionInst(str));
        return cache.get(str).clone();
    }
}
