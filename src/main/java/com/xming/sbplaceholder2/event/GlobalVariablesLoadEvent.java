package com.xming.sbplaceholder2.event;

import com.xming.sbplaceholder2.parser.type.SBElement;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class GlobalVariablesLoadEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    public HashMap<String, SBElement<?>> global_variables;

    public GlobalVariablesLoadEvent(HashMap<String, SBElement<?>> global_variables) {
        this.global_variables = global_variables;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
