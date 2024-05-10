package com.xming.sbplaceholder2.parser.type.element;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.ElementMethod;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBElement;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
import com.xming.sbplaceholder2.parser.type.type.TimeType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;

public class TimeElement extends SBElement<TimeType> {
    @NotNull
    public final Long value;
    public TimeElement() {
        this.value = System.currentTimeMillis();
    }
    public TimeElement(@NotNull Long value) {
        this.value = value;
    }
    @Override
    public Plugin getPlugin() {
        return SBPlaceholder2.plugin;
    }
    @Override
    public String getName() {
        return "Time";
    }
    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public IntElement asInt() {
        return new IntElement(value);
    }

    @Override
    public NumberElement asNumber() {
        return new NumberElement(value.doubleValue());
    }

    @Override
    public TimeElement symbol_add(SBElement<?> other) {
        return new TimeElement(value + other.asInt().value);
    }

    @Override
    public TimeElement symbol_sub(SBElement<?> other) {
        return new TimeElement(value - other.asInt().value);
    }

    @Override
    public Integer symbol_compare(SBElement<?> other) {
        return value.compareTo(other.asInt().value);
    }

    @ElementMethod(name = "format", args = {"String?"}, returnType = "String")
    public SBElement<?> method_abs(Parser parser, EntrustInst... args) {
        String format;
        if (args[0].execute(parser) instanceof VoidElement) {
            format = "yyyy-MM-dd HH:mm:ss";
        } else {
            format = args[0].execute(parser).asString().value;
        }
        String res = new SimpleDateFormat(format).format(this.value);
        return new StringElement(res);
    }

    @ElementMethod(name = "timestamp", returnType = "Int")
    public IntElement method_timestamp(Parser parser, EntrustInst... args) {
        return new IntElement(value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof IntElement && ((IntElement) obj).value.equals(value);
    }
}
