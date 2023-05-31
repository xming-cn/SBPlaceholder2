package com.xming.sbplaceholder2.parser.type.inst;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.type.SBInst;
import com.xming.sbplaceholder2.parser.type.type.PlayerType;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class PlayerInst extends SBInst<PlayerType> {
    @NotNull public final OfflinePlayer value;
    public PlayerInst(@NotNull OfflinePlayer player) {
        this.value = player;
    }
    @Override
    public Plugin getPlugin() {
        return SBPlaceholder2.plugin;
    }
    @Override
    public String getName() {
        return "Player";
    }

    @Override
    public String toString() {
        return value.getName();
    }

    @Override
    public String toDebug() {
        return "Player@" + value.getName();
    }
    @Override
    public StringInst asString() {
        String name = value.getName();
        return new StringInst(name == null ? "null" : name);
    }
    @Override
    public SBInst<?> symbol_equal(SBInst<?> other) {
        return super.symbol_equal(other);
    }
    @Override
    public SBInst<?> symbol_not_equal(SBInst<?> other) {
        return super.symbol_not_equal(other);
    }

    @Override
    public SBInst<?> symbol_getField(String name) {
        return switch (name.toLowerCase()) {
            case "name" -> new StringInst(value.getName());
            case "uuid" -> new StringInst(value.getUniqueId().toString());
            default -> new StringInst("undefined element");
        };
    }
}
