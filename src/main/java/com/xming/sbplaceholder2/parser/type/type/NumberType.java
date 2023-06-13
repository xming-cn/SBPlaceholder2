package com.xming.sbplaceholder2.parser.type.type;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBType;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
import com.xming.sbplaceholder2.parser.type.inst.NumberElement;
import org.bukkit.plugin.Plugin;

public class NumberType extends SBType<NumberElement> {
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
    public NumberElement newInst(Parser parser, EntrustInst... insts) {
        return insts[0].execute(parser).asNumber();
    }
}
