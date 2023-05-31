package com.xming.sbplaceholder2.parser.type.type;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.type.SBType;
import com.xming.sbplaceholder2.parser.type.inst.BoolInst;
import org.bukkit.plugin.Plugin;

public class BoolType extends SBType<BoolInst> {

    @Override
    public Plugin getPlugin() {
        return SBPlaceholder2.plugin;
    }
    @Override
    public String getName() {
        return "Bool";
    }
    @Override
    public BoolInst newInst(String str) {
        if (str.equalsIgnoreCase("true")) {
            return new BoolInst(true);
        }
        return new BoolInst(false);
    }
}
