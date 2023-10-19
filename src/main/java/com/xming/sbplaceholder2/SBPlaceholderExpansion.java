package com.xming.sbplaceholder2;

import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBElement;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class SBPlaceholderExpansion extends PlaceholderExpansion {
    @Override
    public @NotNull String getAuthor() {
        return "xming_jun";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "s";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        Parser parser = new Parser(params, null, -1);
        SBElement<?> result = parser.parse(player);

        return ChatColor.translateAlternateColorCodes('&', result.toString());
    }
}
