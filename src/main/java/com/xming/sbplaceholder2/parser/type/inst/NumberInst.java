package com.xming.sbplaceholder2.parser.type.inst;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.type.SBInst;
import com.xming.sbplaceholder2.parser.type.type.NumberType;
import org.bukkit.plugin.Plugin;

public class NumberInst extends SBInst<NumberType> {
    Float value;
    public NumberInst(Float value) {
        this.value = value;
    }

    @Override
    public Plugin getPlugin() {
        return SBPlaceholder2.plugin;
    }
    @Override
    public String getName() {
        return "Number";
    }
    @Override
    public String toString() {
        return value.toString();
    }
    @Override
    public String toDebug() {
        return "Number@" + value;
    }
    @Override
    public BoolInst asBool() {
        return new BoolInst(value > 0);
    }
    @Override
    public IntInst asInt() {
        return new IntInst(value.intValue());
    }
    @Override
    public NumberInst asNumber() {
        return this;
    }
    @Override
    public StringInst asString() {
        return new StringInst(value.toString());
    }
    @Override
    public NumberInst symbol_add(SBInst<?> other) {
        NumberInst otherNumber = other instanceof NumberInst numberInst ? numberInst : other.asNumber();
        return new NumberInst(value + otherNumber.value);
    }
    @Override
    public NumberInst symbol_sub(SBInst<?> other) {
        NumberInst otherNumber = other instanceof NumberInst numberInst ? numberInst : other.asNumber();
        return new NumberInst(value - otherNumber.value);
    }
    @Override
    public NumberInst symbol_mul(SBInst<?> other) {
        NumberInst otherNumber = other instanceof NumberInst numberInst ? numberInst : other.asNumber();
        return new NumberInst(value * otherNumber.value);
    }
    @Override
    public NumberInst symbol_div(SBInst<?> other) {
        NumberInst otherNumber = other instanceof NumberInst numberInst ? numberInst : other.asNumber();
        return new NumberInst(value / otherNumber.value);
    }
    @Override
    public NumberInst symbol_mod(SBInst<?> other) {
        NumberInst otherNumber = other instanceof NumberInst numberInst ? numberInst : other.asNumber();
        return new NumberInst(value % otherNumber.value);
    }
    @Override
    public Integer symbol_compare(SBInst<?> other) {
        NumberInst otherNumber = other instanceof NumberInst numberInst ? numberInst : other.asNumber();
        return value.compareTo(otherNumber.value);
    }
}
