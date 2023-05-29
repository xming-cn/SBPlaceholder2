package com.xming.sbplaceholder2.parser.type;

import com.xming.sbplaceholder2.parser.Parser;
import org.bukkit.plugin.Plugin;

public abstract class SBType<T extends SBInst<?>> implements TypeInstanceTo<T> {
    protected abstract Plugin getPlugin();
    protected abstract String getName();
    protected abstract T newInst(String str);
}
