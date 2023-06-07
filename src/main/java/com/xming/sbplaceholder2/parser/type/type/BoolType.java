package com.xming.sbplaceholder2.parser.type.type;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.type.SBInst;
import com.xming.sbplaceholder2.parser.type.SBType;
import com.xming.sbplaceholder2.parser.type.inst.BoolInst;
import org.bukkit.plugin.Plugin;

public class BoolType extends SBType<BoolInst> {
    public static BoolType inst = new BoolType();
    private BoolType() {}
    @Override
    public Plugin getPlugin() {
        return SBPlaceholder2.plugin;
    }
    @Override
    public String getName() {
        return "Bool";
    }
    @Override
    public BoolInst newInst(SBInst<?>... insts) {
        return insts[0].asBool();
    }
}
