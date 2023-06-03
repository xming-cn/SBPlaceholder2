package com.xming.sbplaceholder2.parser.type.type;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.type.SBInst;
import com.xming.sbplaceholder2.parser.type.SBType;
import com.xming.sbplaceholder2.parser.type.inst.StringInst;
import org.bukkit.plugin.Plugin;

public class StringType extends SBType<StringInst> {
    @Override
    public Plugin getPlugin() {
        return SBPlaceholder2.plugin;
    }
    @Override
    public String getName() {
        return "String";
    }
    @Override
    public StringInst newInst(SBInst<?>... insts) {
        return insts[0].asString();
    }
}
