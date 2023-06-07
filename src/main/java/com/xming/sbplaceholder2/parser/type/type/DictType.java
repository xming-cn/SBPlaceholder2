package com.xming.sbplaceholder2.parser.type.type;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.type.SBInst;
import com.xming.sbplaceholder2.parser.type.SBType;
import com.xming.sbplaceholder2.parser.type.inst.DictInst;
import org.bukkit.plugin.Plugin;

public class DictType extends SBType<DictInst> {
    public static DictType inst = new DictType();
    private DictType() {}
    @Override
    public Plugin getPlugin() {
        return SBPlaceholder2.plugin;
    }
    @Override
    public String getName() {
        return "Map";
    }
    @Override
    public DictInst newInst(SBInst<?>... insts) {
        return new DictInst(insts);
    }
}
