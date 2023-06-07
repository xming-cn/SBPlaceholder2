package com.xming.sbplaceholder2.parser.type.type;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.type.SBInst;
import com.xming.sbplaceholder2.parser.type.SBType;
import com.xming.sbplaceholder2.parser.type.inst.NumberInst;
import org.bukkit.plugin.Plugin;

public class NumberType extends SBType<NumberInst> {
    public static NumberType inst = new NumberType();
    private NumberType() {}
    @Override
    public Plugin getPlugin() {
        return SBPlaceholder2.plugin;
    }
    @Override
    public String getName() {
        return "Number";
    }
    @Override
    public NumberInst newInst(SBInst<?>... insts) {
        return insts[0].asNumber();
    }
}
