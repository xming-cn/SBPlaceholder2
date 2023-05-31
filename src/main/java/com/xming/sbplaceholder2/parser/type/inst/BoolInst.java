package com.xming.sbplaceholder2.parser.type.inst;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.type.SBInst;
import com.xming.sbplaceholder2.parser.type.type.BoolType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class BoolInst extends SBInst<BoolType> {
    @NotNull public final Boolean value;

    public BoolInst(@NotNull Boolean value) {
        this.value = value;
    }
    @Override
    public Plugin getPlugin() {
        return SBPlaceholder2.plugin;
    }
    @Override
    public String getName() {
        return "Bool";
    }
    @Override
    public String toString() {
        return value.toString();
    }
    @Override
    public BoolInst asBool() {
        return this;
    }
    @Override
    public IntInst asInt() {
        return new IntInst(value ? 1 : 0);
    }
    @Override
    public NumberInst asNumber() {
        return new NumberInst(value ? 1f : 0f);
    }
    @Override
    public StringInst asString() {
        return new StringInst(value.toString());
    }

    @Override
    public BoolInst symbol_and(SBInst<?> other) {
        BoolInst otherBool = other instanceof BoolInst boolInst ? boolInst : other.asBool();
        return new BoolInst(value && otherBool.value);
    }

    @Override
    public BoolInst symbol_or(SBInst<?> other) {
        BoolInst otherBool = other instanceof BoolInst boolInst ? boolInst : other.asBool();
        return new BoolInst(value || otherBool.value);
    }

    @Override
    public BoolInst symbol_not() {
        return new BoolInst(!value);
    }
}
