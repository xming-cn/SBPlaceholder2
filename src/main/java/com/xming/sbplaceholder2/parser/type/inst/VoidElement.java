package com.xming.sbplaceholder2.parser.type.inst;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.type.SBElement;
import com.xming.sbplaceholder2.parser.type.type.VoidType;
import org.bukkit.plugin.Plugin;

public class VoidElement extends SBElement<VoidType> {
    public static final VoidElement instance = new VoidElement();
    private VoidElement() {
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

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof VoidElement;
    }
}
