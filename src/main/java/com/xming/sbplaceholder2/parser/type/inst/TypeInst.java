package com.xming.sbplaceholder2.parser.type.inst;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBInst;
import com.xming.sbplaceholder2.parser.type.SBType;
import com.xming.sbplaceholder2.parser.type.TypeManager;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
import com.xming.sbplaceholder2.parser.type.type.TypeType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class TypeInst extends SBInst<TypeType> {
    SBType<?> value;
    public TypeInst(@NotNull SBType<?> value) {
        this.value = value;
    }
    public TypeInst(String value) {
        this.value = TypeManager.getInstance().getType(value);
    }
    @Override
    public Plugin getPlugin() {
        return SBPlaceholder2.plugin;
    }
    @Override
    public String getName() {
        return "Type";
    }
    @Override
    public String toString() {
        return value.getName();
    }
    @Override
    public Object clone() {
        return new TypeInst(value);
    }
    @Override
    public SBInst<?> symbol_call(Parser parser, EntrustInst... args) {
        return value.newInst(Arrays.stream(args)
                .map(it -> it.execute(parser, parser.getPlayer().value))
                .toArray(SBInst<?>[]::new));
    }
    @Override
    public int hashCode() {
        return value.hashCode();
    }
    @Override
    public boolean equals(Object obj) {
        return obj instanceof TypeInst typeInst && typeInst.value.equals(value);
    }
}
