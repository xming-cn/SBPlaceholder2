package com.xming.sbplaceholder2.parser.type.inst;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.InstMethod;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBInst;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
import com.xming.sbplaceholder2.parser.type.type.IntType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class IntInst extends SBInst<IntType> {
    @NotNull public final Integer value;
    public IntInst(@NotNull Integer value) {
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
    public BoolInst asBool() {
        return BoolInst.fromBool(value > 0);
    }
    @Override
    public IntInst asInt() {
        return this;
    }
    @Override
    public NumberInst asNumber() {
        return new NumberInst(value.floatValue());
    }
    @Override
    public IntInst symbol_add(SBInst<?> other) {
        IntInst otherInt = other instanceof IntInst intInst ? intInst : other.asInt();
        return new IntInst(value + otherInt.value);
    }
    @Override
    public IntInst symbol_sub(SBInst<?> other) {
        IntInst otherInt = other instanceof IntInst intInst ? intInst : other.asInt();
        return new IntInst(value - otherInt.value);
    }
    @Override
    public IntInst symbol_mul(SBInst<?> other) {
        IntInst otherInt = other instanceof IntInst intInst ? intInst : other.asInt();
        return new IntInst(value * otherInt.value);
    }
    @Override
    public NumberInst symbol_div(SBInst<?> other) {
        IntInst otherInt = other instanceof IntInst intInst ? intInst : other.asInt();
        return new NumberInst(((float)value) / otherInt.value);
    }
    @Override
    public IntInst symbol_double_div(SBInst<?> other) {
        IntInst otherInt = other instanceof IntInst intInst ? intInst : other.asInt();
        return new IntInst(value / otherInt.value);
    }
    @Override
    public NumberInst symbol_double_mul(SBInst<?> other) {
        IntInst otherInt = other instanceof IntInst intInst ? intInst : other.asInt();
        return new NumberInst((float)Math.pow((double)value, (double)otherInt.value));
    }
    @Override
    public Integer symbol_compare(SBInst<?> other) {
        IntInst otherInt = other instanceof IntInst intInst ? intInst : other.asInt();
        return value.compareTo(otherInt.value);
    }
    @InstMethod(name = "abs", returnType = "Int")
    public SBInst<?> method_abs(Parser parser, EntrustInst... args) {
        return new IntInst(Math.abs(value));
    }
    @InstMethod(name = "pow", args = {"Int"}, returnType = "Number")
    public SBInst<?> method_pow(Parser parser, EntrustInst... args) {
        PlayerInst player = parser.getPlayer();
        IntInst arg1 = args[0].execute(parser, player.value).asInt();
        return new NumberInst((float) Math.pow(value, arg1.value));
    }
    @InstMethod(name = "mod", args = {"Int"}, returnType = "Int")
    public SBInst<?> method_mod(Parser parser, EntrustInst... args) {
        PlayerInst player = parser.getPlayer();
        IntInst arg1 = args[0].execute(parser, player.value).asInt();
        return new IntInst(value % arg1.value);
    }
    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof IntInst intInst && intInst.value.equals(value);
    }
}
