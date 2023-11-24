package com.xming.sbplaceholder2.parser.type.inst;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.ElementMethod;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBElement;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
import com.xming.sbplaceholder2.parser.type.type.DurationType;
import org.bukkit.plugin.Plugin;

public class DurationElement extends SBElement<DurationType> {
    Long value;

    public DurationElement(Long value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public Plugin getPlugin() {
        return SBPlaceholder2.plugin;
    }

    @Override
    public String getName() {
        return "Duration";
    }

    @ElementMethod(name = "sec", returnType = "Int")
    public IntElement method_sec(Parser parser, EntrustInst... args) {
        return new IntElement(value / 1000L);
    }
    @ElementMethod(name = "min", returnType = "Int")
    public IntElement method_min(Parser parser, EntrustInst... args) {
        return new IntElement(value / (1000L * 60L));
    }
    @ElementMethod(name = "hour", returnType = "Int")
    public IntElement method_hour(Parser parser, EntrustInst... args) {
        return new IntElement(value / (1000L * 60L * 60L));
    }
}
