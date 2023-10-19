package com.xming.sbplaceholder2.parser.type.inst;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.ElementMethod;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBElement;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
import com.xming.sbplaceholder2.parser.type.type.NumberType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class NumberElement extends SBElement<NumberType> {
    public final @NotNull Double value;
    public NumberElement(@NotNull Double value) {
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
    public BoolElement asBool() {
        return BoolElement.fromBool(value > 0);
    }
    @Override
    public IntElement asInt() {
        return new IntElement(value.intValue());
    }
    @Override
    public NumberElement asNumber() {
        return this;
    }
    @Override
    public StringElement asString() {
        return new StringElement(value.toString());
    }
    @Override
    public NumberElement symbol_add(SBElement<?> other) {
        return new NumberElement(value + other.asNumber().value);
    }
    @Override
    public NumberElement symbol_sub(SBElement<?> other) {
        return new NumberElement(value - other.asNumber().value);
    }
    @Override
    public NumberElement symbol_mul(SBElement<?> other) {
        return new NumberElement(value * other.asNumber().value);
    }
    @Override
    public NumberElement symbol_div(SBElement<?> other) {
        return new NumberElement(value / other.asNumber().value);
    }
    @Override
    public NumberElement symbol_double_div(SBElement<?> other) {
        return new NumberElement((double)(int)(value / other.asNumber().value));
    }
    @Override
    public NumberElement symbol_double_mul(SBElement<?> other) {
        return new NumberElement(Math.pow(value, other.asNumber().value));
    }
    @Override
    public Integer symbol_compare(SBElement<?> other) {
        return value.compareTo(other.asNumber().value);
    }
    @ElementMethod(name = "round", returnType = "Int")
    public IntElement method_round(Parser parser, EntrustInst... args) {
        return new IntElement((int) Math.round(value));
    }
    @ElementMethod(name = "abs", returnType = "Number")
    public NumberElement method_abs(Parser parser, EntrustInst... args) {
        return new NumberElement(Math.abs(value));
    }
    @ElementMethod(name = "mod", returnType = "Number", args = {"Number"})
    public NumberElement method_mod(Parser parser, EntrustInst... args) {
        double other = args[0].execute(parser).asNumber().value;
        return new NumberElement(value % other);
    }
    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof NumberElement && ((NumberElement) obj).value.equals(value);
    }
}
