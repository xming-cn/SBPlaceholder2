package com.xming.sbplaceholder2.parser.type.type;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBType;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
import com.xming.sbplaceholder2.parser.type.inst.BoolElement;
import org.bukkit.plugin.Plugin;

public class BoolType extends SBType<BoolElement> {
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
    public BoolElement newInst(Parser parser, EntrustInst... insts) {
        return insts[0].execute(parser).asBool();
    }
}
