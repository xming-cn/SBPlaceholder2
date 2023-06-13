package com.xming.sbplaceholder2.parser.type.inst;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBElement;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
import com.xming.sbplaceholder2.parser.type.type.FunctionType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.function.Function;

public class FunctionElement extends SBElement<FunctionType> {
    @NotNull public final Function<SBElement<?>[], SBElement<?>> value;

    public FunctionElement(@NotNull Function<SBElement<?>[], SBElement<?>> func) {
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
    public SBElement<?> symbol_call(Parser parser, EntrustInst... args) {
        return value.apply(Arrays.stream(args)
                .map(it -> it.execute(parser))
                .toArray(SBElement<?>[]::new)
        );
    }
}
