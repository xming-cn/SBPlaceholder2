package com.xming.sbplaceholder2.parser.type;

import org.bukkit.plugin.Plugin;

public abstract class SBType<T extends SBInst<?>> implements TypeInstanceTo<T> {
    protected abstract Plugin getPlugin();
    protected abstract String getName();
    public abstract T newInst(SBInst<?>... insts);
}
