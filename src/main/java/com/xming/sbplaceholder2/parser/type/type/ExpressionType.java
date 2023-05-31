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
    protected ExpressionInst newInst(String str) {
        // TODO: 2021/8/3
        if (cache.containsKey(str)) return cache.get(str);
        ExpressionInst expression = new ExpressionInst(null, str);
        cache.put(str, expression);
        return expression;
    }
}
