package com.xming.sbplaceholder2.parser.type.inst;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBInst;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
import com.xming.sbplaceholder2.parser.type.type.FunctionType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.function.Function;

public class FunctionInst extends SBInst<FunctionType> {
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
    public SBInst<?> symbol_call(Parser parser, EntrustInst... args) {
        return value.apply(Arrays.stream(args)
                .map(it -> it.execute(parser, parser.getPlayer().value))
                .toArray(SBInst<?>[]::new)
        );
    }
}
