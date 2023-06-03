package com.xming.sbplaceholder2.parser.type.inst;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBInst;
import com.xming.sbplaceholder2.parser.type.type.StringType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class FunctionInst extends SBInst<StringType> {
    @NotNull public final Function<SBInst<?>[], SBInst<?>> value;

    public FunctionInst(@NotNull Function<SBInst<?>[], SBInst<?>> func) {
        this.value = func;
    }

    @Override
    public Plugin getPlugin() {
        return SBPlaceholder2.plugin;
    }
    @Override
    public String getName() {
        return "Function";
    }

    @Override
    public String toString() {
        return "func";
    }

    @Override
    public SBInst<?> symbol_call(Parser parser, SBInst<?>... args) {
        return value.apply(args);
    }
}
