package com.xming.sbplaceholder2.parser.type.inst;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.type.SBInst;
import com.xming.sbplaceholder2.parser.type.type.IntType;
import org.bukkit.plugin.Plugin;

public class IntInst extends SBInst<IntType> {
    Integer value;
    public IntInst(Integer value) {
        this.value = value;
    }
    @Override
    public Plugin getPlugin() {
        return SBPlaceholder2.plugin;
    }
    @Override
    public String getName() {
        return "Int";
    }
    @Override
    public String toString() {
        return value.toString();
    }
    @Override
    public String toDebug() {
        return "Int@" + value;
    }
    @Override
    public BoolInst asBool() {
        return new BoolInst(value > 0);
    }
    @Override
    public IntInst asInt() {
        return this;
    }
    @Override
    public NumberInst asNumber() {
        return new NumberInst(value.floatValue());
    }
    @Override
    public StringInst asString() {
        return new StringInst(value.toString());
    }
    @Override
    public IntInst symbol_add(SBInst<?> other) {
        IntInst otherInt = other instanceof IntInst intInst ? intInst : other.asInt();
        return new IntInst(value + otherInt.value);
    }
    @Override
    public IntInst symbol_sub(SBInst<?> other) {
        IntInst otherInt = other instanceof IntInst intInst ? intInst : other.asInt();
        return new IntInst(value - otherInt.value);
    }
    @Override
    public IntInst symbol_mul(SBInst<?> other) {
        IntInst otherInt = other instanceof IntInst intInst ? intInst : other.asInt();
        return new IntInst(value * otherInt.value);
    }
    @Override
    public IntInst symbol_div(SBInst<?> other) {
        IntInst otherInt = other instanceof IntInst intInst ? intInst : other.asInt();
        return new IntInst(value / otherInt.value);
    }
    @Override
    public IntInst symbol_mod(SBInst<?> other) {
        IntInst otherInt = other instanceof IntInst intInst ? intInst : other.asInt();
        return new IntInst(value % otherInt.value);
    }
    @Override
    public Integer symbol_compare(SBInst<?> other) {
        IntInst otherInt = other instanceof IntInst intInst ? intInst : other.asInt();
        return value.compareTo(otherInt.value);
    }
}
