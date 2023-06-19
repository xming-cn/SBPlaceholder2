package com.xming.sbplaceholder2.parser.type;

import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Method;

public abstract class SBType<T extends SBElement<?>> implements TypeInstanceTo<T> {
    public abstract Plugin getPlugin();
    public abstract String getName();
    public abstract String getDescription();
    public abstract T newInst(Parser parser, EntrustInst... insts);
    public void register(String key) {
        TypeManager.getInstance().register(key, this);
    }
    public void expand(String name, String[] alias, Method method, String[] args) {
        TypeManager.getInstance().expand(this, name, alias, method, args);
    }
}
