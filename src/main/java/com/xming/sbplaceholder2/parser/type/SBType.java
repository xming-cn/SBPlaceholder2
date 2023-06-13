package com.xming.sbplaceholder2.parser.type;

import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
import org.bukkit.plugin.Plugin;

public abstract class SBType<T extends SBElement<?>> implements TypeInstanceTo<T> {
    public abstract Plugin getPlugin();
    public abstract String getName();
    public abstract T newInst(Parser parser, EntrustInst... insts);
    public void register(String key) {
        TypeManager.getInstance().register(key, this);
    }
}
