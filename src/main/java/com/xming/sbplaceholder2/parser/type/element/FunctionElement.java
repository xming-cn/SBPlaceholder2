package com.xming.sbplaceholder2.parser.type.element;

import com.google.common.collect.Maps;
import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBElement;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
import com.xming.sbplaceholder2.parser.type.type.FunctionType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class FunctionElement extends SBElement<FunctionType> {
    @NotNull public final Maps.EntryTransformer<Parser, SBElement<?>[], SBElement<?>> value;

    public FunctionElement(@NotNull Maps.EntryTransformer<Parser, SBElement<?>[], SBElement<?>> func) {
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
        return value.transformEntry(parser,
                Arrays.stream(args)
                        .map(it -> it.execute(parser))
                        .toArray(SBElement<?>[]::new)
        );
    }
}
