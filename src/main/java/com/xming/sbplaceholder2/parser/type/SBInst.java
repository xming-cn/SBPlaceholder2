package com.xming.sbplaceholder2.parser.type;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.exception.UnsupportedOperationException;
import com.xming.sbplaceholder2.exception.UnsupportedSingleOperationException;
import com.xming.sbplaceholder2.exception.UnsupportedTypeCastException;
import com.xming.sbplaceholder2.parser.InstMethod;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
import com.xming.sbplaceholder2.parser.type.inst.*;
import org.bukkit.plugin.Plugin;

public abstract class SBInst<T extends SBType<?>> implements TypeInstanceOf<T>, Cloneable {
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
        return new StringInst(toString());
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
    public Integer symbol_compare(SBInst<?> other) {throw new UnsupportedOperationException(this, other, "compare");}
    public SBInst<?> symbol_equal(SBInst<?> other) {
        return BoolInst.fromBool(symbol_compare(other) == 0);
    }
    public SBInst<?> symbol_not_equal(SBInst<?> other) {
        return symbol_equal(other).symbol_not();
    }
    public SBInst<?> symbol_greater(SBInst<?> other) {
        return BoolInst.fromBool(symbol_compare(other) > 0);
    }
    public SBInst<?> symbol_less(SBInst<?> other) {
        return BoolInst.fromBool(symbol_compare(other) < 0);
    }
    public SBInst<?> symbol_egreater(SBInst<?> other) {
        return BoolInst.fromBool(symbol_compare(other) >= 0);
    }
    public SBInst<?> symbol_eless(SBInst<?> other) {
        return BoolInst.fromBool(symbol_compare(other) <= 0);
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
    public SBInst<?> symbol_getField(Parser parser, String name) {
        throw new UnsupportedSingleOperationException(this, "getField");
    }
    public SBInst<?> symbol_call(Parser parser, SBInst<?>... args) {
        throw new UnsupportedSingleOperationException(this, "symbol_call");
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new CloneNotSupportedException("Clone not supported");
        }
    }
    @InstMethod(name = "?", alias = {"ifVoid"}, args = {"Any"})
    public SBInst<?> ifVoid(Parser parser, EntrustInst[] args) {
        PlayerInst player = parser.getPlayer();
        if (this.equals(VoidInst.instance)) {
            return args[0].execute(parser, player.value);
        } else return this;
    }
    @InstMethod(name = "asString", alias = {"toString"}, returnType = "Bool")
    public StringInst asString(Parser parser, EntrustInst[] args) {
        return asString();
    }
}
