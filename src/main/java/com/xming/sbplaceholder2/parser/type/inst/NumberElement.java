package com.xming.sbplaceholder2.parser.type.inst;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.ElementMethod;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.CalculableElement;
import com.xming.sbplaceholder2.parser.type.SBElement;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
import com.xming.sbplaceholder2.parser.type.type.NumberType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class NumberElement extends CalculableElement<NumberType> {
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
        return new IntElement(value.longValue());
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
    public Integer symbol_compare(SBElement<?> other) {
        return value.compareTo(other.asNumber().value);
    }
    @ElementMethod(name = "round", returnType = "Int")
    public IntElement method_round(Parser parser, EntrustInst... args) {
        return new IntElement(Math.round(value));
    }
    @ElementMethod(name = "abs", returnType = "Number")
    public NumberElement method_abs(Parser parser, EntrustInst... args) {
        return new NumberElement(Math.abs(value));
    }
    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof NumberElement && ((NumberElement) obj).value.equals(value);
    }

    @Override
    public boolean decimal() {
        return true;
    }

    @Override
    public double value() {
        return value;
    }
}
