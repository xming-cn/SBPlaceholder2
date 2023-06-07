package com.xming.sbplaceholder2.parser.type.type;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.type.SBInst;
import com.xming.sbplaceholder2.parser.type.SBType;
import com.xming.sbplaceholder2.parser.type.inst.FunctionInst;
import org.bukkit.plugin.Plugin;

public class FunctionType extends SBType<FunctionInst> {
    public static FunctionType inst = new FunctionType();
    private FunctionType() {}
    @Override
    public Plugin getPlugin() {
        return SBPlaceholder2.plugin;
    }
    @Override
    public String getName() {
        return "Function";
    }
    @Override
    public FunctionInst newInst(SBInst<?>... insts) {
        return new FunctionInst((SBInst<?>... inst) -> insts[0].asString());
    }
}
