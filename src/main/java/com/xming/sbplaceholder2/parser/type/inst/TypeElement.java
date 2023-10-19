package com.xming.sbplaceholder2.parser.type.inst;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBElement;
import com.xming.sbplaceholder2.parser.type.SBType;
import com.xming.sbplaceholder2.parser.type.TypeManager;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
import com.xming.sbplaceholder2.parser.type.type.TypeType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class TypeElement extends SBElement<TypeType> {
    SBType<?> value;
    public TypeElement(@NotNull SBType<?> value) {
        this.value = value;
    }
    public TypeElement(String value) {
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
        return new TypeElement(value);
    }
    @Override
    public SBElement<?> symbol_call(Parser parser, EntrustInst... args) {
        return value.newInst(parser, args);
    }
    @Override
    public int hashCode() {
        return value.hashCode();
    }
    @Override
    public boolean equals(Object obj) {
        return obj instanceof TypeElement && ((TypeElement) obj).value.equals(value);
    }
}
