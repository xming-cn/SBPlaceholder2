package com.xming.sbplaceholder2;

import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBElement;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
import com.xming.sbplaceholder2.parser.type.inst.ExpressionElement;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

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
        long startTime = System.currentTimeMillis();
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
            SBPlaceholder2.logger.info("start parse: " + params);
            SBPlaceholder2.logger.info("debug level: " + debug);
        }
        Parser parser = new Parser(params, null, debug);

        long endTime = 0;
        if (debug >= 0) {
            if (parser.getExpression() instanceof ExpressionElement expressionInst) {
                SBPlaceholder2.logger.info(Arrays.toString(expressionInst.operator));
                for (EntrustInst entrustInst : expressionInst.entrust) {
                    if (entrustInst != null) System.out.println(entrustInst);
                }
            } else {
                SBPlaceholder2.logger.info(parser.getExpression().toString());
            }

            endTime = System.currentTimeMillis();
        }
        SBElement<?> result = parser.parse(player);
        if (debug >= 0) SBPlaceholder2.logger.info("run time: " + (endTime - startTime) + "ms");

        return result.toString();
    }
}
