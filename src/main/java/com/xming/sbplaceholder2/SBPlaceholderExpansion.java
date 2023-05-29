package com.xming.sbplaceholder2;

import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBInst;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class SBPlaceholderExpansion extends PlaceholderExpansion {
    @Override
    public @NotNull String getAuthor() {
        return "xming_jun";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "s2";
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
        long startTime = System.currentTimeMillis();
        SBInst<?> result = new Parser(params).parse(player);
        long endTime = System.currentTimeMillis();
        System.out.println("run time: " + (endTime - startTime) + "ms");
        return result.toString();
    }
}
