package com.xming.sbplaceholder2.parser.type.element;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.ElementMethod;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.CalculableElement;
import com.xming.sbplaceholder2.parser.type.SBElement;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
import com.xming.sbplaceholder2.parser.type.type.IntType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class IntElement extends CalculableElement<IntType> {
    @NotNull public final Long value;
    public IntElement(@NotNull Long value) {
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
        return new NumberElement(value.doubleValue());
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
        return obj instanceof IntElement && ((IntElement) obj).value.equals(value);
    }

    @Override
    public double value() {
        return value;
    }
}
