package com.xming.sbplaceholder2.parser.type.inst;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.InstMethod;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBInst;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
import com.xming.sbplaceholder2.parser.type.type.StringType;
import me.clip.placeholderapi.PlaceholderAPI;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class StringInst extends SBInst<StringType> {
    @NotNull public final String value;

    public StringInst(@NotNull Character str) {
        this.value = String.valueOf(str);
    }
    public StringInst(@NotNull String str) {
        this.value = str;
    }

    @Override
    public Plugin getPlugin() {
        return SBPlaceholder2.plugin;
    }
    @Override
    public String getName() {
        return "String";
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public BoolInst asBool() {
        return BoolInst.fromBool(!StringUtils.isBlank(value));
    }

    @Override
    public IntInst asInt() {
        return new IntInst(Integer.parseInt(value));
    }

    @Override
    public NumberInst asNumber() {
        return new NumberInst(Float.parseFloat(value));
    }

    @Override
    public StringInst asString() {
        return this;
    }

    @Override
    public StringInst symbol_add(SBInst<?> other) {
        StringInst otherString = other instanceof StringInst stringInst ? stringInst : other.asString();
        return new StringInst(value + otherString.value);
    }

    @Override
    public StringInst symbol_mul(SBInst<?> other) {
        IntInst otherInt = other instanceof IntInst intInst ? intInst : other.asInt();
        return new StringInst(value.repeat(otherInt.value));
    }

    @Override
    public Integer symbol_compare(SBInst<?> other) {
        StringInst otherString = other instanceof StringInst stringInst ? stringInst : other.asString();
        return value.compareTo(otherString.value);
    }

    @Override
    public SBInst<?> symbol_getField(Parser parser, String name) {
        if (name.contains(":")) {
            String[] split = name.split(":");
            int start = Integer.parseInt(split[0]);
            int end = Integer.parseInt(split[1]);
            return new StringInst(value.substring(start, end));
        } else {
            return new StringInst(value.charAt(Integer.parseInt(name)));
        }
    }
    @InstMethod(name = "papi", args = {"Player?"}, returnType = "String")
    public StringInst method_papi(Parser parser, EntrustInst[] args) {
        OfflinePlayer p;
        if (args[0].execute(parser, parser.getPlayer().value) instanceof PlayerInst target) {
            p = target.value;
        } else {
            p = parser.getPlayer().value;
        }
        return new StringInst(PlaceholderAPI.setPlaceholders(p, "%" + value + "%"));
    }
    @InstMethod(name = "color", args = {"Char?"}, returnType = "String")
    public StringInst method_color(Parser parser, EntrustInst[] args) {
        OfflinePlayer player = parser.getPlayer().value;
        SBInst<?> arg1 = args[0].execute(parser, player);
        char c = arg1.equals(VoidInst.instance) ? '&' : arg1.asString().value.charAt(0);
        return new StringInst(ChatColor.translateAlternateColorCodes(c, value));
    }
    @InstMethod(name = "slice", alias = "substring", args = {"Int", "Int?"}, returnType = "String")
    public StringInst method_slice(Parser parser, EntrustInst[] args) {
        OfflinePlayer player = parser.getPlayer().value;
        IntInst arg1 = args[0].execute(parser, player).asInt();
        SBInst<?> arg2 = args[1].execute(parser, player);
        int start;
        int end;
        if (arg2.equals(VoidInst.instance)) {
            start = 0;
            end = arg1.value;
        } else {
            start = arg1.value;
            end = arg2.asInt().value;
        }
        return new StringInst(value.substring(start, end));
    }
    @InstMethod(name = "length", returnType = "Int")
    public IntInst method_length(Parser parser, EntrustInst[] args) {
        return new IntInst(value.length());
    }
    @InstMethod(name = "replace", args = {"String", "String"}, returnType = "String")
    public StringInst method_replace(Parser parser, EntrustInst[] args) {
        OfflinePlayer player = parser.getPlayer().value;
        String from = args[0].execute(parser, player).asString().value;
        String to = args[1].execute(parser, player).asString().value;
        return new StringInst(value.replace(from, to));
    }
    @InstMethod(name = "replaceAll", args = {"String", "String"}, returnType = "String")
    public StringInst method_replaceAll(Parser parser, EntrustInst[] args) {
        OfflinePlayer player = parser.getPlayer().value;
        String from = args[0].execute(parser, player).asString().value;
        String to = args[1].execute(parser, player).asString().value;
        return new StringInst(value.replaceAll(from, to));
    }
    @InstMethod(name = "match", args = {"String"}, returnType = "Bool")
    public BoolInst method_match(Parser parser, EntrustInst[] args) {
        OfflinePlayer player = parser.getPlayer().value;
        String regex = args[0].execute(parser, player).asString().value;
        return BoolInst.fromBool(value.matches(regex));
    }
    @InstMethod(name = "indexOf", args = {"String"}, returnType = "Int")
    public IntInst method_indexOf(Parser parser, EntrustInst[] args) {
        OfflinePlayer player = parser.getPlayer().value;
        String str = args[0].execute(parser, player).asString().value;
        return new IntInst(value.indexOf(str));
    }
    @InstMethod(name = "lastIndexOf", args = {"String"}, returnType = "Int")
    public IntInst method_lastIndexOf(Parser parser, EntrustInst[] args) {
        OfflinePlayer player = parser.getPlayer().value;
        String str = args[0].execute(parser, player).asString().value;
        return new IntInst(value.lastIndexOf(str));
    }
    @InstMethod(name = "contains", args = {"String"}, returnType = "Bool")
    public BoolInst method_contains(Parser parser, EntrustInst[] args) {
        OfflinePlayer player = parser.getPlayer().value;
        String str = args[0].execute(parser, player).asString().value;
        return BoolInst.fromBool(value.contains(str));
    }
    @InstMethod(name = "split", args = {"String"}, returnType = "List")
    public ListInst method_split(Parser parser, EntrustInst[] args) {
        OfflinePlayer player = parser.getPlayer().value;
        String regex = args[0].execute(parser, player).asString().value;
        String[] split = value.split(regex);
        StringInst[] insts = Arrays.stream(split).map(StringInst::new).toArray(StringInst[]::new);
        return new ListInst(insts);
    }
    @InstMethod(name = "startsWith", args = {"String"}, returnType = "Bool")
    public BoolInst method_startsWith(Parser parser, EntrustInst[] args) {
        OfflinePlayer player = parser.getPlayer().value;
        String str = args[0].execute(parser, player).asString().value;
        return BoolInst.fromBool(value.startsWith(str));
    }
    @InstMethod(name = "endsWith", args = {"String"}, returnType = "Bool")
    public BoolInst method_endsWith(Parser parser, EntrustInst[] args) {
        OfflinePlayer player = parser.getPlayer().value;
        String str = args[0].execute(parser, player).asString().value;
        return BoolInst.fromBool(value.endsWith(str));
    }
    @InstMethod(name = "toLower", returnType = "String")
    public StringInst method_toLower(Parser parser, EntrustInst[] args) {
        return new StringInst(value.toLowerCase());
    }
    @InstMethod(name = "toUpper", returnType = "String")
    public StringInst method_toUpper(Parser parser, EntrustInst[] args) {
        return new StringInst(value.toUpperCase());
    }
    @InstMethod(name = "trim", returnType = "String")
    public StringInst method_trim(Parser parser, EntrustInst[] args) {
        return new StringInst(value.trim());
    }
}
