package com.xming.sbplaceholder2.parser.type.type;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBType;
import com.xming.sbplaceholder2.parser.type.inst.IntInst;
import org.bukkit.plugin.Plugin;

public class IntType extends SBType<IntInst> {
    @Override
    public Plugin getPlugin() {
        return SBPlaceholder2.plugin;
    }
    @Override
    public String getName() {
        return "Int";
    }
    @Override
    protected IntInst newInst(String str) {
        return new IntInst(Integer.parseInt(str));
    }
}
