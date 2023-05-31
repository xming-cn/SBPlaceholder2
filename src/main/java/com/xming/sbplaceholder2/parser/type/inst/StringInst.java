package com.xming.sbplaceholder2.parser.type.inst;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.type.SBInst;
import com.xming.sbplaceholder2.parser.type.type.StringType;
import org.apache.commons.lang.StringUtils;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class StringInst extends SBInst<StringType> {
    @NotNull public final String value;

    public StringInst(@NotNull String str) {
        this.value = str;
    }

    @Override
    public Plugin getPlugin() {
        return SBPlaceholder2.plugin;
    }
    @Override
    public String getName() {
        return "String";
    }

    @Override
    public String toString() {
        return value;
    }
    @Override
    public String toDebug() {
        return "String@" + value;
    }

    @Override
    public BoolInst asBool() {
        return new BoolInst(!StringUtils.isBlank(value));
    }

    @Override
    public IntInst asInt() {
        return new IntInst(Integer.parseInt(value));
    }

    @Override
    public NumberInst asNumber() {
        return new NumberInst(Float.parseFloat(value));
    }

    @Override
    public StringInst asString() {
        return this;
    }

    @Override
    public StringInst symbol_add(SBInst<?> other) {
        StringInst otherString = other instanceof StringInst stringInst ? stringInst : other.asString();
        return new StringInst(value + otherString.value);
    }

    @Override
    public StringInst symbol_mul(SBInst<?> other) {
        IntInst otherInt = other instanceof IntInst intInst ? intInst : other.asInt();
        return new StringInst(value.repeat(otherInt.value));
    }

    @Override
    public Integer symbol_compare(SBInst<?> other) {
        StringInst otherString = other instanceof StringInst stringInst ? stringInst : other.asString();
        return value.compareTo(otherString.value);
    }
}
