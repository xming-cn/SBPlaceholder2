package com.xming.sbplaceholder2.parser.type.type;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBElement;
import com.xming.sbplaceholder2.parser.type.SBType;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
import com.xming.sbplaceholder2.parser.type.inst.DictElement;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

public class DictType extends SBType<DictElement> {
    public static DictType inst = new DictType();
    private DictType() {}
    @Override
    public Plugin getPlugin() {
        return SBPlaceholder2.plugin;
    }
    @Override
    public String getName() {
        return "Dict";
    }
    @Override
    public DictElement newInst(Parser parser, EntrustInst... insts) {
        return new DictElement(Arrays.stream(insts)
                .map(inst -> inst.execute(parser))
                .toArray(SBElement[]::new));
    }
}
