package com.xming.sbplaceholder2.parser.type.type;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBType;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
import com.xming.sbplaceholder2.parser.type.inst.IntElement;
import org.bukkit.plugin.Plugin;

public class IntType extends SBType<IntElement> {
    public static IntType inst = new IntType();
    private IntType() {}
    @Override
    public Plugin getPlugin() {
        return SBPlaceholder2.plugin;
    }
    @Override
    public String getName() {
        return "Int";
    }
    @Override
    public IntElement newInst(Parser parser, EntrustInst... insts) {
        return insts[0].execute(parser).asInt();
    }
}
