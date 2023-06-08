package com.xming.sbplaceholder2.parser.type.inst;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.InstMethod;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBInst;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
import com.xming.sbplaceholder2.parser.type.type.NumberType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class NumberInst extends SBInst<NumberType> {
    @NotNull public final Float value;
    public NumberInst(@NotNull Float value) {
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
    public BoolInst asBool() {
        return BoolInst.fromBool(value > 0);
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
    public NumberInst symbol_double_div(SBInst<?> other) {
        NumberInst otherNumber = other instanceof NumberInst numberInst ? numberInst : other.asNumber();
        return new NumberInst((float)(int)(value / otherNumber.value));
    }
    @Override
    public NumberInst symbol_double_mul(SBInst<?> other) {
        IntInst otherInt = other instanceof IntInst intInst ? intInst : other.asInt();
        return new NumberInst((float)Math.pow(value, otherInt.value));
    }
    @Override
    public Integer symbol_compare(SBInst<?> other) {
        NumberInst otherNumber = other instanceof NumberInst numberInst ? numberInst : other.asNumber();
        return value.compareTo(otherNumber.value);
    }
    @InstMethod(name = "round", returnType = "Int")
    public IntInst method_round(Parser parser, EntrustInst... args) {
        return new IntInst(Math.round(value));
    }
    @InstMethod(name = "abs", returnType = "Number")
    public NumberInst method_abs(Parser parser, EntrustInst... args) {
        return new NumberInst(Math.abs(value));
    }
    @InstMethod(name = "mod", returnType = "Number", args = {"Number"})
    public NumberInst method_mod(Parser parser, EntrustInst... args) {
        double other = (double) args[0].execute(parser, null).asNumber().value;
        return new NumberInst((float) (value % other));
    }
    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof NumberInst numberInst && numberInst.value.equals(value);
    }
}
