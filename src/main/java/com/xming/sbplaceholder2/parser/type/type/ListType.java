package com.xming.sbplaceholder2.parser.type.type;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.type.SBInst;
import com.xming.sbplaceholder2.parser.type.SBType;
import com.xming.sbplaceholder2.parser.type.inst.ListInst;
import org.bukkit.plugin.Plugin;

public class ListType extends SBType<ListInst> {
    @Override
    public Plugin getPlugin() {
        return SBPlaceholder2.plugin;
    }
    @Override
    public String getName() {
        return "List";
    }
    @Override
    public ListInst newInst(SBInst<?>... insts) {
        return new ListInst(insts);
    }
}
