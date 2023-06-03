package com.xming.sbplaceholder2.parser.type.type;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.type.SBInst;
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
    public IntInst newInst(SBInst<?>... insts) {
        return insts[0].asInt();
    }
}
