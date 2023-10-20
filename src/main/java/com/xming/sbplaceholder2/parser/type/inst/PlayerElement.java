package com.xming.sbplaceholder2.parser.type.inst;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.ElementMethod;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBElement;
import com.xming.sbplaceholder2.parser.type.type.PlayerType;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

public class PlayerElement extends SBElement<PlayerType> {
    public static PlayerElement voidPlayer = new PlayerElement(null);
    public final OfflinePlayer value;
    public PlayerElement(OfflinePlayer player) {
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
    public SBElement<?> symbol_equal(SBElement<?> other) {
        return super.symbol_equal(other);
    }
    @Override
    public SBElement<?> symbol_not_equal(SBElement<?> other) {
        return super.symbol_not_equal(other);
    }

    @Override
    public SBElement<?> symbol_getField(Parser parser, String name) {
        if (value == null) return new VoidElement("玩家不存在");
        switch (name.toLowerCase()) {
            case "name": return new StringElement(Objects.requireNonNull(value.getName()));
            case "uuid": return new StringElement(value.getUniqueId().toString());
            default: return new StringElement("undefined element");
        }
    }
    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @ElementMethod(name = "online", alias = {"isOnline"}, returnType = "Bool")
    public SBElement<?> method_online() {
        return BoolElement.fromBool(value.isOnline());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PlayerElement) {
            return value.equals(((PlayerElement) obj).value);
        }
        return false;
    }
}
