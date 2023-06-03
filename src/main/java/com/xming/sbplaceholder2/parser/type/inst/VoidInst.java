package com.xming.sbplaceholder2.parser.type.inst;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.type.SBInst;
import com.xming.sbplaceholder2.parser.type.type.VoidType;
import org.bukkit.plugin.Plugin;

public class VoidInst extends SBInst<VoidType> {
    public static final VoidInst instance = new VoidInst();
    private VoidInst() {
    }
    @Override
    public Plugin getPlugin() {
        return SBPlaceholder2.plugin;
    }
    @Override
    public String getName() {
        return "Void";
    }
    @Override
    public String toString() {
        return "void";
    }
    @Override
    public Object clone() {
        return instance;
    }
}
