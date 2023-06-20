package com.xming.sbplaceholder2.event;

import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBElement;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class FastElementBuildEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private SBElement<?> result = null;
    final private Parser parser;
    final private String raw_string;
    public FastElementBuildEvent(Parser parser, String raw_string) {
        this.parser = parser;
        this.raw_string = raw_string;
    }

    public SBElement<?> getResult() {
        return result;
    }

    public void setResult(SBElement<?> result) {
        this.result = result;
    }

    public Parser getParser() {
        return parser;
    }

    public String getRaw_string() {
        return raw_string;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Contract(pure = true)
    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
