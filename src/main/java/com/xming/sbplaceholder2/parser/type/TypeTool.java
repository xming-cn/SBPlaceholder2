package com.xming.sbplaceholder2.parser.type;

import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.inst.BoolInst;
import com.xming.sbplaceholder2.parser.type.inst.IntInst;
import com.xming.sbplaceholder2.parser.type.inst.NumberInst;
import com.xming.sbplaceholder2.parser.type.inst.StringInst;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.OfflinePlayer;

public class TypeTool {
    public static SBInst<?> parse(OfflinePlayer player, String str) {
        String trim = str.trim();
        if (trim.startsWith("(") && trim.endsWith(")")) {
            String substring = trim.substring(1, trim.length() - 1);
            return new Parser(substring).parse(player);
        } else if (trim.startsWith("\"") && trim.endsWith("\"")) {
            return new StringInst(trim.substring(1, trim.length() - 1));
        } else if (trim.startsWith("'") && trim.endsWith("'")) {
            return new StringInst(trim.substring(1, trim.length() - 1));
        } else if (NumberUtils.isDigits(trim)) {
            return new IntInst(NumberUtils.toInt(trim));
        } else if (NumberUtils.isNumber(trim)) {
            return new NumberInst(NumberUtils.toFloat(trim));
        }  else if (trim.equalsIgnoreCase("true")) {
            return new BoolInst(true);
        } else if (trim.equalsIgnoreCase("false")) {
            return new BoolInst(false);
        } else return new StringInst(str);
    }
}
