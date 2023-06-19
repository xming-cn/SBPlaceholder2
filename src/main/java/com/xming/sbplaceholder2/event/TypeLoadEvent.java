package com.xming.sbplaceholder2.event;

import com.xming.sbplaceholder2.parser.type.SBType;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class TypeLoadEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled = false;
    private final SBType<?> type;

    public TypeLoadEvent(SBType<?> type) {
        this.type = type;
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

    public SBType<?> getType() {
        return type;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
