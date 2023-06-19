package com.xming.sbplaceholder2.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class SBPlaceholderLoadEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    public SBPlaceholderLoadEvent() {
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
