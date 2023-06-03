package com.xming.sbplaceholder2.parser.type.inst;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBInst;
import com.xming.sbplaceholder2.parser.type.type.PlayerType;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

public class PlayerInst extends SBInst<PlayerType> {
    public static PlayerInst voidPlayer = new PlayerInst(null);
    public final OfflinePlayer value;
    public PlayerInst(OfflinePlayer player) {
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
    public SBInst<?> symbol_equal(SBInst<?> other) {
        return super.symbol_equal(other);
    }
    @Override
    public SBInst<?> symbol_not_equal(SBInst<?> other) {
        return super.symbol_not_equal(other);
    }

    @Override
    public SBInst<?> symbol_getField(Parser parser, String name) {
        if (value == null) return VoidInst.instance;
        return switch (name.toLowerCase()) {
            case "name" -> new StringInst(Objects.requireNonNull(value.getName()));
            case "uuid" -> new StringInst(value.getUniqueId().toString());
            default -> new StringInst("undefined element");
        };
    }
}
