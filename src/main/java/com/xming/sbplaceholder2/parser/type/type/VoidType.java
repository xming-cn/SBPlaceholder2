package com.xming.sbplaceholder2.parser.type.type;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.type.SBInst;
import com.xming.sbplaceholder2.parser.type.SBType;
import com.xming.sbplaceholder2.parser.type.inst.VoidInst;
import org.bukkit.plugin.Plugin;

public class VoidType extends SBType<VoidInst> {
    public static VoidType inst = new VoidType();
    private VoidType() {}

    @Override
    public Plugin getPlugin() {
        return SBPlaceholder2.plugin;
    }
    @Override
    public String getName() {
        return "Bool";
    }
    @Override
    public VoidInst newInst(SBInst<?>... insts) {
        return VoidInst.instance;
    }
}
