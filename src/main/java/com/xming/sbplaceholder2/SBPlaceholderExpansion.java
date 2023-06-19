package com.xming.sbplaceholder2;

import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBElement;
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
        // if param starts with "(debug)" or "(debug-<depth>)" then print debug info
        int debug = -1;
        if (params.matches("\\(debug(-\\d)?\\).+")) {
            if (params.matches("\\(debug-\\d\\).+")) {
                debug = Integer.parseInt(params.substring(7, 8));
                params = params.substring(9);
            } else {
                debug = 100;
                params = params.substring(7);
            }
        }

        Parser parser = new Parser(params, null, debug);
        SBElement<?> result = parser.parse(player);

        return result.toString();
    }
}
