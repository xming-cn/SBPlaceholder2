package com.xming.sbplaceholder2.parser.type.type;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBElement;
import com.xming.sbplaceholder2.parser.type.SBType;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
import com.xming.sbplaceholder2.parser.type.inst.ListElement;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

public class ListType extends SBType<ListElement> {
    public static ListType inst = new ListType();
    private ListType() {}
    @Override
    public Plugin getPlugin() {
        return SBPlaceholder2.plugin;
    }
    @Override
    public String getName() {
        return "List";
    }

    @Override
    public String getDescription() {
        return "列表, 用于存储多个元素。";
    }

    @Override
    public ListElement newInst(Parser parser, EntrustInst... insts) {
        return new ListElement(Arrays.stream(insts)
                .map(inst -> inst.execute(parser))
                .toArray(SBElement[]::new));
    }
}
