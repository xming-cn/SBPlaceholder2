package com.xming.sbplaceholder2.parser.type.element;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.ElementMethod;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBElement;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
import com.xming.sbplaceholder2.parser.type.type.ListType;
import com.xming.sbplaceholder2.parser.type.type.StringType;
import me.clip.placeholderapi.PlaceholderAPI;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringElement extends SBElement<StringType> {
    @NotNull public final String value;
    static public List<String> false_strings = new ArrayList<>();

    public StringElement(@NotNull Character str) {
        this.value = String.valueOf(str);
    }
    public StringElement(@NotNull String str) {
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
    public BoolElement asBool() {
        if (false_strings.contains(value)) return BoolElement.fromBool(false);
        return BoolElement.fromBool(!StringUtils.isBlank(value));
    }

    @Override
    public IntElement asInt() {
        return new IntElement(Long.parseLong(value));
    }

    @Override
    public NumberElement asNumber() {
        return new NumberElement(Double.parseDouble(value));
    }

    @Override
    public StringElement asString() {
        return this;
    }

    @Override
    public StringElement symbol_add(SBElement<?> other) {
        return new StringElement(value + other.asString().value);
    }

    @Override
    public StringElement symbol_mul(SBElement<?> other) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < other.asInt().value; i++) {
            result.append(value);
        }
        return new StringElement(result.toString());
    }

    @Override
    public Integer symbol_compare(SBElement<?> other) {
        return value.compareTo(other.asString().value);
    }

    @Override
    public SBElement<?> symbol_getField(Parser parser, String name) {
        if (name.contains(":")) {
            String[] split = name.split(":");
            int start = Integer.parseInt(split[0]);
            int end = Integer.parseInt(split[1]);
            return new StringElement(value.substring(start, end));
        } else {
            return new StringElement(value.charAt(Integer.parseInt(name)));
        }
    }
    @ElementMethod(name = "papi", args = {"Player?"}, returnType = "String")
    public StringElement method_papi(Parser parser, EntrustInst[] args) {
        SBElement<?> execute = args[0].execute(parser);
        OfflinePlayer p = execute instanceof PlayerElement ? ((PlayerElement) execute).value : parser.getPlayer().value;
        return new StringElement(PlaceholderAPI.setPlaceholders(p, "%" + value + "%"));
    }
    @ElementMethod(name = "color", args = {"String?"}, returnType = "String")
    public StringElement method_color(Parser parser, EntrustInst[] args) {
        SBElement<?> arg1 = args[0].execute(parser);
        char c = arg1 instanceof VoidElement ? '&' : arg1.asString().value.charAt(0);
        return new StringElement(ChatColor.translateAlternateColorCodes(c, value));
    }
    @ElementMethod(name = "slice", alias = "substring", args = {"Int", "Int?"}, returnType = "String")
    // 返回从 start 到 end 的子字符串，包括 start 位置的字符，但不包括 end 位置的字符。
    public StringElement method_slice(Parser parser, EntrustInst[] args) {
        IntElement arg1 = args[0].execute(parser).asInt();
        SBElement<?> arg2 = args[1].execute(parser);
        int start;
        int end;
        if (arg2 instanceof VoidElement) {
            start = 0;
            end = Math.toIntExact(arg1.value);
        } else {
            start = Math.toIntExact(arg1.value);
            end = Math.toIntExact(arg2.asInt().value);
        }
        return new StringElement(value.substring(start, end));
    }
    @ElementMethod(name = "length", returnType = "Int")
    public IntElement method_length(Parser parser, EntrustInst[] args) {
        return new IntElement((long) value.length());
    }
    @ElementMethod(name = "replace", args = {"String", "String"}, returnType = "String")
    public StringElement method_replace(Parser parser, EntrustInst[] args) {
        String from = args[0].execute(parser).asString().value;
        String to = args[1].execute(parser).asString().value;
        return new StringElement(value.replace(from, to));
    }
    @ElementMethod(name = "replaceAll", args = {"String", "String"}, returnType = "String")
    public StringElement method_replaceAll(Parser parser, EntrustInst[] args) {
        String from = args[0].execute(parser).asString().value;
        String to = args[1].execute(parser).asString().value;
        return new StringElement(value.replaceAll(from, to));
    }
    @ElementMethod(name = "match", args = {"String"}, returnType = "Bool")
    public BoolElement method_match(Parser parser, EntrustInst[] args) {
        String regex = args[0].execute(parser).asString().value;
        return BoolElement.fromBool(value.matches(regex));
    }
    @ElementMethod(name = "indexOf", args = {"String"}, returnType = "Int")
    public IntElement method_indexOf(Parser parser, EntrustInst[] args) {
        String str = args[0].execute(parser).asString().value;
        return new IntElement((long) value.indexOf(str));
    }
    @ElementMethod(name = "lastIndexOf", args = {"String"}, returnType = "Int")
    public IntElement method_lastIndexOf(Parser parser, EntrustInst[] args) {
        String str = args[0].execute(parser).asString().value;
        return new IntElement((long) value.lastIndexOf(str));
    }
    @ElementMethod(name = "contains", args = {"String"}, returnType = "Bool")
    public BoolElement method_contains(Parser parser, EntrustInst[] args) {
        String str = args[0].execute(parser).asString().value;
        return BoolElement.fromBool(value.contains(str));
    }
    @ElementMethod(name = "split", args = {"String?"}, returnType = "List")
    public ListElement method_split(Parser parser, EntrustInst[] args) {
        SBElement<?> arg1 = args[0].execute(parser);
        String[] split = value.split(arg1 instanceof VoidElement ? " " : arg1.asString().value);
        StringElement[] insts = Arrays.stream(split).map(StringElement::new).toArray(StringElement[]::new);
        return new ListElement(insts);
    }
    @ElementMethod(name = "startsWith", args = {"String"}, returnType = "Bool")
    public BoolElement method_startsWith(Parser parser, EntrustInst[] args) {
        String str = args[0].execute(parser).asString().value;
        return BoolElement.fromBool(value.startsWith(str));
    }
    @ElementMethod(name = "endsWith", args = {"String"}, returnType = "Bool")
    public BoolElement method_endsWith(Parser parser, EntrustInst[] args) {
        String str = args[0].execute(parser).asString().value;
        return BoolElement.fromBool(value.endsWith(str));
    }
    @ElementMethod(name = "toLower", returnType = "String")
    public StringElement method_toLower(Parser parser, EntrustInst[] args) {
        return new StringElement(value.toLowerCase());
    }
    @ElementMethod(name = "toUpper", returnType = "String")
    public StringElement method_toUpper(Parser parser, EntrustInst[] args) {
        return new StringElement(value.toUpperCase());
    }
    @ElementMethod(name = "trim", returnType = "String")
    public StringElement method_trim(Parser parser, EntrustInst[] args) {
        return new StringElement(value.trim());
    }
    @ElementMethod(name = "chars", returnType = "List")
    public ListElement method_chars(Parser parser, EntrustInst[] args) {
        return ListType.inst.newInst(parser,
                value.chars()
                        .mapToObj(i -> new EntrustInst(new StringElement(String.valueOf((char) i))))
                        .toArray(EntrustInst[]::new));
    }
    @ElementMethod(name = "isInt", returnType = "Bool")
    public BoolElement method_isInt(Parser parser, EntrustInst[] args) {
        return BoolElement.fromBool(NumberUtils.isDigits(value));
    }
    @ElementMethod(name = "isNumber", returnType = "Bool")
    public BoolElement method_isNumber(Parser parser, EntrustInst[] args) {
        return BoolElement.fromBool(NumberUtils.isNumber(value));
    }
    @Override
    public int hashCode() {
        return value.hashCode();
    }
    @Override
    public boolean equals(Object obj) {
        return obj instanceof StringElement && value.equals(((StringElement) obj).value);
    }
}
