package com.xming.sbplaceholder2.parser.type.inst;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.ElementMethod;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBElement;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
import com.xming.sbplaceholder2.parser.type.type.BoolType;
import org.bukkit.plugin.Plugin;

public class BoolElement extends SBElement<BoolType> {
    public static BoolElement trueInstance = new BoolElement();
    public static BoolElement falseInstance = new BoolElement();
    @Override
    public Plugin getPlugin() {
        return SBPlaceholder2.plugin;
    }
    @Override
    public String getName() {
        return "Bool";
    }
    @Override
    public String toString() {
        return toBool().toString();
    }
    @Override
    public BoolElement asBool() {
        return this;
    }
    @Override
    public IntElement asInt() {
        return new IntElement(toBool() ? 1 : 0);
    }
    @Override
    public NumberElement asNumber() {
        return new NumberElement(toBool() ? 1f : 0f);
    }
    @Override
    public BoolElement symbol_and(SBElement<?> other) {
        BoolElement otherBool = other instanceof BoolElement boolInst ? boolInst : other.asBool();
        return fromBool(toBool() && otherBool.toBool());
    }
    @Override
    public BoolElement symbol_or(SBElement<?> other) {
        BoolElement otherBool = other instanceof BoolElement boolInst ? boolInst : other.asBool();
        return fromBool(toBool() || otherBool.toBool());
    }
    @Override
    public BoolElement symbol_not() {
        return fromBool(!toBool());
    }

    @Override
    public Object clone() {
        return fromBool(toBool());
    }

    @ElementMethod(name = "if", args = {"Bool", "Any", "Any"}, returnType = "Any")
    public SBElement<?> method_if(Parser parser, EntrustInst... args) {
        return toBool() ? args[0].execute(parser) : args[1].execute(parser);
    }

    public Boolean toBool() {
        return this == trueInstance;
    }

    static public BoolElement fromBool(Boolean bool) {
        return bool ? trueInstance : falseInstance;
    }

    @Override
    public int hashCode() {
        return toBool() ? 1 : 0;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof BoolElement boolInst && boolInst.toBool() == toBool();
    }
}
