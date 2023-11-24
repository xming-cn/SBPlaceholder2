package com.xming.sbplaceholder2.parser.type.inst;

import com.google.common.base.Joiner;
import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.ElementMethod;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBElement;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
import com.xming.sbplaceholder2.parser.type.type.ListType;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;

public class ListElement extends SBElement<ListType> {
    ArrayList<SBElement<?>> value = new ArrayList<>();
    public ListElement(SBElement<?>... insts) {
        value.addAll(Arrays.asList(insts));
    }
    @Override
    public Plugin getPlugin() {
        return SBPlaceholder2.plugin;
    }
    @Override
    public String getName() {
        return "List";
    }
    @Override
    public String toString() {
        return "[" + Joiner.on(", ").join(value.stream().map(SBElement::toString).toArray(String[]::new)) + "]";
    }
    @Override
    public String toDebug() {
        if (value.size() < 6) {
            return "List@[" + Joiner.on(", ").join(value.stream().map(SBElement::toDebug).toArray(String[]::new)) + "]";
        } else {
            return "List@[" + value.get(0).toDebug() + ", " + value.get(1).toDebug() + ", ...(" + (value.size() - 3) + "), " + value.get(value.size() - 1).toDebug() + " ]";
        }
    }
    public void addAll(SBElement<?>... insts) {
        value.addAll(Arrays.asList(insts));
    }
    public void append(SBElement<?> inst) {
        value.add(inst);
    }
    @Override
    public SBElement<?> symbol_add(SBElement<?> other) {
        value.add(other);
        return this;
    }
    @Override
    public SBElement<?> symbol_mul(SBElement<?> other) {
        ListElement result = new ListElement();
        for (int i = 0; i < other.asInt().value; i++) {
            result.addAll(value.toArray(new SBElement<?>[0]));
        }
        return super.symbol_mul(other);
    }
    @Override
    public SBElement<?> symbol_getField(Parser parser, String name) {
        return value.get(Integer.parseInt(name));
    }
    @ElementMethod(name = "size", alias = {"length"}, returnType = "Int")
    public IntElement method_size(Parser parser, EntrustInst... args) {
        return new IntElement((long) value.size());
    }
    @ElementMethod(name = "get", args = {"Int"}, returnType = "Any")
    public SBElement<?> method_get(Parser parser, EntrustInst... args) {
        PlayerElement player = parser.getPlayer();
        IntElement arg1 = args[0].execute(parser).asInt();
        return value.get(Math.toIntExact(arg1.value));
    }
    @ElementMethod(name = "set", args = {"Int", "Any"}, returnType = "List")
    public ListElement method_set(Parser parser, EntrustInst... args) {
        PlayerElement player = parser.getPlayer();
        IntElement arg1 = args[0].execute(parser).asInt();
        SBElement<?> args2 = args[1].execute(parser);
        value.set(Math.toIntExact(arg1.value), args2);
        return this;
    }
    @ElementMethod(name = "join", args = {"String"}, returnType = "String")
    public StringElement method_join(Parser parser, EntrustInst... args) {
        PlayerElement player = parser.getPlayer();
        StringElement arg1 = args[0].execute(parser).asString();
        return new StringElement(String.join(arg1.value, value.stream().map(SBElement::toString).toArray(String[]::new)));
    }
    @ElementMethod(name = "append", args = {"Any"}, returnType = "List")
    public ListElement method_append(Parser parser, EntrustInst... args) {
        PlayerElement player = parser.getPlayer();
        SBElement<?> args1 = args[0].execute(parser);
        value.add(args1);
        return this;
    }
    @ElementMethod(name = "sort", returnType = "List")
    public ListElement method_sort(Parser parser, EntrustInst... args) {
        value.sort(SBElement::symbol_compare);
        return this;
    }
    @ElementMethod(name = "reverse", returnType = "List")
    public ListElement method_reverse(Parser parser, EntrustInst... args) {
        ArrayList<SBElement<?>> result = new ArrayList<>();
        for (int i = value.size() - 1; i >= 0; i--) {
            result.add(value.get(i));
        }
        return new ListElement(result.toArray(new SBElement<?>[0]));
    }
    @ElementMethod(name = "remove", args = {"Int"}, returnType = "List")
    public ListElement method_remove(Parser parser, EntrustInst... args) {
        PlayerElement player = parser.getPlayer();
        IntElement arg1 = args[0].execute(parser).asInt();
        value.remove(Math.toIntExact(arg1.value));
        return this;
    }
    @ElementMethod(name = "insert", args = {"Int", "Any"}, returnType = "List")
    public ListElement method_insert(Parser parser, EntrustInst... args) {
        PlayerElement player = parser.getPlayer();
        IntElement arg1 = args[0].execute(parser).asInt();
        SBElement<?> args2 = args[1].execute(parser);
        value.add(Math.toIntExact(arg1.value), args2);
        return this;
    }
    @ElementMethod(name = "contains", args = {"Any"}, returnType = "Bool")
    public BoolElement method_contains(Parser parser, EntrustInst... args) {
        PlayerElement player = parser.getPlayer();
        SBElement<?> arg1 = args[0].execute(parser);
        return BoolElement.fromBool(value.contains(arg1));
    }
    @ElementMethod(name = "index", args = {"Any"}, returnType = "Int")
    public IntElement method_index(Parser parser, EntrustInst... args) {
        PlayerElement player = parser.getPlayer();
        SBElement<?> arg1 = args[0].execute(parser);
        return new IntElement((long) value.indexOf(arg1));
    }
    @ElementMethod(name = "lastIndex", args = {"Any"}, returnType = "Int")
    public IntElement method_lastIndex(Parser parser, EntrustInst... args) {
        PlayerElement player = parser.getPlayer();
        SBElement<?> arg1 = args[0].execute(parser);
        return new IntElement((long) value.lastIndexOf(arg1));
    }
    @ElementMethod(name = "random", returnType = "Any")
    public SBElement<?> method_random(Parser parser, EntrustInst... args) {
        return value.get((int) (Math.random() * value.size()));
    }
    @ElementMethod(name = "map", args = {"Entrust"}, returnType = "List")
    public ListElement method_map(Parser parser, EntrustInst... args) {
        PlayerElement player = parser.getPlayer();
        String each;
        EntrustInst run;
        if (args.length > 1) {
            each = args[0].execute(parser).asString().value;
            run = args[1];
        } else {
            each = "it";
            run = args[0];
        }
        ListElement result = new ListElement();
        for (SBElement<?> inst : value) {
            parser.getVariables().put(each, inst);
            result.append(run.execute(parser));
        }
        return result;
    }
    @ElementMethod(name = "filter", args = {"Entrust"}, returnType = "List")
    public ListElement method_filter(Parser parser, EntrustInst... args) {
        PlayerElement player = parser.getPlayer();
        String each;
        EntrustInst run;
        if (args.length > 1) {
            each = args[0].execute(parser).asString().value;
            run = args[1];
        } else {
            each = "it";
            run = args[0];
        }
        ListElement result = new ListElement();
        for (SBElement<?> inst : value) {
            parser.getVariables().put(each, inst);
            if (run.execute(parser).asBool().toBool()) {
                result.append(inst);
            }
        }
        return result;
    }
}
