package com.xming.sbplaceholder2.parser.type.inst;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.InstMethod;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBInst;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
import com.xming.sbplaceholder2.parser.type.type.BoolType;
import org.bukkit.plugin.Plugin;

public class BoolInst extends SBInst<BoolType> {
    public static BoolInst trueInstance = new BoolInst();
    public static BoolInst falseInstance = new BoolInst();
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
    public BoolInst asBool() {
        return this;
    }
    @Override
    public IntInst asInt() {
        return new IntInst(toBool() ? 1 : 0);
    }
    @Override
    public NumberInst asNumber() {
        return new NumberInst(toBool() ? 1f : 0f);
    }
    @Override
    public BoolInst symbol_and(SBInst<?> other) {
        BoolInst otherBool = other instanceof BoolInst boolInst ? boolInst : other.asBool();
        return fromBool(toBool() && otherBool.toBool());
    }
    @Override
    public BoolInst symbol_or(SBInst<?> other) {
        BoolInst otherBool = other instanceof BoolInst boolInst ? boolInst : other.asBool();
        return fromBool(toBool() || otherBool.toBool());
    }
    @Override
    public BoolInst symbol_not() {
        return fromBool(!toBool());
    }

    @Override
    public Object clone() {
        return fromBool(toBool());
    }

    @InstMethod(name = "if", args = {"Bool", "Any", "Any"}, returnType = "Any")
    public SBInst<?> method_if(Parser parser, EntrustInst... args) {
        return toBool() ? args[0].execute(parser, parser.getPlayer().value)
                : args[1].execute(parser, parser.getPlayer().value);
    }

    public Boolean toBool() {
        return this == trueInstance;
    }

    static public BoolInst fromBool(Boolean bool) {
        return bool ? trueInstance : falseInstance;
    }
}
