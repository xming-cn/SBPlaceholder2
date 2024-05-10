package com.xming.sbplaceholder2.parser.type.type;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBType;
import com.xming.sbplaceholder2.parser.type.element.VoidElement;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
import org.bukkit.plugin.Plugin;

public class VoidType extends SBType<VoidElement> {
    public static VoidType inst = new VoidType();
    private VoidType() {}

    @Override
    public Plugin getPlugin() {
        return SBPlaceholder2.plugin;
    }
    @Override
    public String getName() {
        return "Void";
    }

    @Override
    public String getDescription() {
        return "吞噬一切的虚空。";
    }

    @Override
    public VoidElement newInst(Parser parser, EntrustInst... insts) {
        return new VoidElement("由 Void 类型创建");
    }
}
