package com.xming.sbplaceholder2.parser.type;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.exception.UnsupportedOperationException;
import com.xming.sbplaceholder2.exception.UnsupportedSingleOperationException;
import com.xming.sbplaceholder2.exception.UnsupportedTypeCastException;
import com.xming.sbplaceholder2.parser.type.inst.BoolInst;
import com.xming.sbplaceholder2.parser.type.inst.IntInst;
import com.xming.sbplaceholder2.parser.type.inst.NumberInst;
import com.xming.sbplaceholder2.parser.type.inst.StringInst;
import org.bukkit.plugin.Plugin;

public abstract class SBInst<T extends SBType<?>> implements TypeInstanceOf<T> {
    public abstract Plugin getPlugin();
    public abstract String getName();
    public String toDebug() {
        if (getPlugin().equals(SBPlaceholder2.plugin))
            return getName() + "@" + this;
        return getPlugin().getName() + "." + getName() + "@" + this;
    }
    public BoolInst asBool() {
        throw new UnsupportedTypeCastException(this, "Bool");
    }
    public IntInst asInt() {
        throw new UnsupportedTypeCastException(this, "Int");
    }
    public NumberInst asNumber() {
        throw new UnsupportedTypeCastException(this, "Number");
    }
    public StringInst asString() {
        throw new UnsupportedTypeCastException(this, "String");
    }
    public SBInst<?> symbol_add(SBInst<?> other) {
        throw new UnsupportedOperationException(this, other, "+");
    }
    public SBInst<?> symbol_sub(SBInst<?> other) {
        throw new UnsupportedOperationException(this, other, "-");
    }
    public SBInst<?> symbol_mul(SBInst<?> other) {
        throw new UnsupportedOperationException(this, other, "*");
    }
    public SBInst<?> symbol_div(SBInst<?> other) {
        throw new UnsupportedOperationException(this, other, "/");
    }
    public SBInst<?> symbol_mod(SBInst<?> other) {
        throw new UnsupportedOperationException(this, other, "%");
    }
    public Integer symbol_compare(SBInst<?> other) {throw new UnsupportedOperationException(this, other, "compare");}
    public SBInst<?> symbol_equal(SBInst<?> other) {
        return new BoolInst(symbol_compare(other) == 0);
    }
    public SBInst<?> symbol_not_equal(SBInst<?> other) {
        return symbol_equal(other).symbol_not();
    }
    public SBInst<?> symbol_greater(SBInst<?> other) {
        return new BoolInst(symbol_compare(other) > 0);
    }
    public SBInst<?> symbol_less(SBInst<?> other) {
        return new BoolInst(symbol_compare(other) < 0);
    }
    public SBInst<?> symbol_egreater(SBInst<?> other) {
        return new BoolInst(symbol_compare(other) >= 0);
    }
    public SBInst<?> symbol_eless(SBInst<?> other) {
        return new BoolInst(symbol_compare(other) <= 0);
    }
    public SBInst<?> symbol_and(SBInst<?> other) {
        throw new UnsupportedOperationException(this, other, "&&");
    }
    public SBInst<?> symbol_or(SBInst<?> other) {
        throw new UnsupportedOperationException(this, other, "||");
    }
    public SBInst<?> symbol_not() {
        throw new UnsupportedSingleOperationException(this, "!");
    }
    public SBInst<?> symbol_getField(String name) {
        throw new UnsupportedSingleOperationException(this, "getField");
    }
    public SBInst<?> symbol_runMethod(String name, SBInst<?>... args) {
        throw new UnsupportedSingleOperationException(this, "runMethod");
    }
    public SBInst<?> symbol_call(SBInst<?>... args) {
        throw new UnsupportedSingleOperationException(this, "symbol_call");
    }
}
