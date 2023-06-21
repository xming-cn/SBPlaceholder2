package com.xming.sbplaceholder2.parser.type.inst;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.ElementMethod;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBElement;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
import com.xming.sbplaceholder2.parser.type.type.IntType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class IntElement extends SBElement<IntType> {
    @NotNull public final Integer value;
    public IntElement(@NotNull Integer value) {
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
    public BoolElement asBool() {
        return BoolElement.fromBool(value > 0);
    }
    @Override
    public IntElement asInt() {
        return this;
    }

    @Override
    public NumberElement asNumber() {
        return new NumberElement(value.floatValue());
    }

    @Override
    public IntElement symbol_add(SBElement<?> other) {
        return new IntElement(value + other.asInt().value);
    }

    @Override
    public IntElement symbol_sub(SBElement<?> other) {
        return new IntElement(value - other.asInt().value);
    }

    @Override
    public IntElement symbol_mul(SBElement<?> other) {
        return new IntElement(value * other.asInt().value);
    }

    @Override
    public NumberElement symbol_div(SBElement<?> other) {
        return new NumberElement(((float)value) / other.asInt().value);
    }

    @Override
    public IntElement symbol_double_div(SBElement<?> other) {
        return new IntElement(value / other.asInt().value);
    }

    @Override
    public NumberElement symbol_double_mul(SBElement<?> other) {
        return new NumberElement((float)Math.pow((double)value, other.asNumber().value));
    }

    @Override
    public Integer symbol_compare(SBElement<?> other) {
        return value.compareTo(other.asInt().value);
    }

    @ElementMethod(name = "abs", returnType = "Int")
    public SBElement<?> method_abs(Parser parser, EntrustInst... args) {
        return new IntElement(Math.abs(value));
    }

    @ElementMethod(name = "mod", args = {"Int"}, returnType = "Int")
    public SBElement<?> method_mod(Parser parser, EntrustInst... args) {
        IntElement arg1 = args[0].execute(parser).asInt();
        return new IntElement(value % arg1.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof IntElement intInst && intInst.value.equals(value);
    }
}
