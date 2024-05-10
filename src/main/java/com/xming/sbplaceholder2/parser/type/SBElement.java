package com.xming.sbplaceholder2.parser.type;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.exception.UnsupportedOperationException;
import com.xming.sbplaceholder2.exception.UnsupportedSingleOperationException;
import com.xming.sbplaceholder2.exception.UnsupportedTypeCastException;
import com.xming.sbplaceholder2.parser.ElementMethod;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.element.*;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
import org.bukkit.plugin.Plugin;

public abstract class SBElement<T extends SBType<?>> implements TypeInstanceOf<T>, Cloneable {
    public abstract Plugin getPlugin();
    public abstract String getName();
    public String toDebug() {
        if (getPlugin().equals(SBPlaceholder2.plugin))
            return getName() + "@" + this;
        return getPlugin().getName() + "." + getName() + "@" + this;
    }
    public BoolElement asBool() {
        throw new UnsupportedTypeCastException(this, "Bool");
    }
    public IntElement asInt() {
        throw new UnsupportedTypeCastException(this, "Int");
    }
    public NumberElement asNumber() {
        throw new UnsupportedTypeCastException(this, "Number");
    }
    public StringElement asString() {
        return new StringElement(toString());
    }
    public SBElement<?> symbol_add(SBElement<?> other) {
        throw new UnsupportedOperationException(this, other, "+");
    }
    public SBElement<?> symbol_sub(SBElement<?> other) {
        throw new UnsupportedOperationException(this, other, "-");
    }
    public SBElement<?> symbol_mul(SBElement<?> other) {
        throw new UnsupportedOperationException(this, other, "*");
    }
    public SBElement<?> symbol_double_mul(SBElement<?> other) {
        throw new UnsupportedOperationException(this, other, "**");
    }
    public SBElement<?> symbol_div(SBElement<?> other) {
        throw new UnsupportedOperationException(this, other, "/");
    }
    public SBElement<?> symbol_double_div(SBElement<?> other) {
        throw new UnsupportedOperationException(this, other, "//");
    }
    public Integer symbol_compare(SBElement<?> other) {throw new UnsupportedOperationException(this, other, "compare");}
    public SBElement<?> symbol_equal(SBElement<?> other) {
        return BoolElement.fromBool(symbol_compare(other) == 0);
    }
    public SBElement<?> symbol_not_equal(SBElement<?> other) {
        return symbol_equal(other).symbol_not();
    }
    public SBElement<?> symbol_greater(SBElement<?> other) {
        return BoolElement.fromBool(symbol_compare(other) > 0);
    }
    public SBElement<?> symbol_less(SBElement<?> other) {
        return BoolElement.fromBool(symbol_compare(other) < 0);
    }
    public SBElement<?> symbol_egreater(SBElement<?> other) {
        return BoolElement.fromBool(symbol_compare(other) >= 0);
    }
    public SBElement<?> symbol_eless(SBElement<?> other) {
        return BoolElement.fromBool(symbol_compare(other) <= 0);
    }
    public SBElement<?> symbol_and(SBElement<?> other) {
        throw new UnsupportedOperationException(this, other, "&&");
    }
    public SBElement<?> symbol_or(SBElement<?> other) {
        throw new UnsupportedOperationException(this, other, "||");
    }
    public SBElement<?> symbol_not() {
        throw new UnsupportedSingleOperationException(this, "!");
    }
    public SBElement<?> symbol_getField(Parser parser, String name) {
        throw new UnsupportedSingleOperationException(this, "getField");
    }
    public SBElement<?> symbol_call(Parser parser, EntrustInst... args) {
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
    @ElementMethod(name = "ifVoid", alias = {"?"}, args = {"Any"})
    public SBElement<?> method_ifVoid(Parser parser, EntrustInst... args) {
        if (this instanceof VoidElement) {
            return args[0].execute(parser);
        } else return this;
    }
    @ElementMethod(name = "asString", alias = {"toString"}, returnType = "String")
    public StringElement method_asString(Parser parser, EntrustInst... args) {
        return asString();
    }
    @ElementMethod(name = "debug", alias = {"asDebug", "toDebug"}, returnType = "String")
    public StringElement method_debug(Parser parser, EntrustInst... args) {
        return new StringElement(toDebug());
    }
    @ElementMethod(name = "type", alias = {"class"}, returnType = "Type")
    public SBElement<?> method_type(Parser parser, EntrustInst... args) {
        return new TypeElement(this.getName());
    }
}
