package com.xming.sbplaceholder2.parser.type;

import org.bukkit.plugin.Plugin;

public abstract class SBType<T extends SBInst<?>> implements TypeInstanceTo<T> {
    public abstract Plugin getPlugin();
    public abstract String getName();
    public abstract T newInst(SBInst<?>... insts);
    public void register(String key) {
        TypeManager.getInstance().register(key, this);
    }
}
