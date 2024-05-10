package com.xming.sbplaceholder2.parser.type.element;

import com.google.common.base.Joiner;
import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.ElementMethod;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBElement;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
import com.xming.sbplaceholder2.parser.type.type.DictType;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class DictElement extends SBElement<DictType> {
    HashMap<SBElement<?>, SBElement<?>> value = new HashMap<>();
    public DictElement(SBElement<?>... insts) {
        for (int i = 0; i < insts.length; i += 2) {
            value.put(insts[i], insts[i + 1]);
        }
    }
    @Override
    public Plugin getPlugin() {
        return SBPlaceholder2.plugin;
    }
    @Override
    public String getName() {
        return "Dict";
    }
    @Override
    public String toString() {
        return "{" + Joiner.on(", ").join(
                value.entrySet().stream().map(entry -> entry.getKey().toString() + ": " +
                        entry.getValue().toString()).toArray(String[]::new)
                ) + "}";
    }
    @Override
    public String toDebug() {
        if (value.size() < 6) {
            return "Dict@{" + Joiner.on(", ").join(
                    value.entrySet().stream().map(entry -> entry.getKey().toDebug() + ": " +
                            entry.getValue().toDebug()).toArray(String[]::new)
            ) + "}";
        } else {
            Optional<String> reduce = value.entrySet().stream().limit(3).map(entry -> entry.getKey().toDebug() + ": " +
                    entry.getValue().toDebug()).reduce((a, b) -> a + ", " + b);
            return reduce.map(s -> "Dict@{" + s + ", ...(" + (value.size() - 3) + "), " +
                    value.entrySet().stream().skip(value.size() - 1).map(entry -> entry.getKey().toDebug() + ": " +
                            entry.getValue().toDebug()).reduce((a, b) -> a + ", " + b).get() + "}").orElse("Dict@{}");
        }
    }
    public void put(SBElement<?> key, SBElement<?> value) {
        this.value.put(key, value);
    }
    @Override
    public SBElement<?> symbol_getField(Parser parser, String name) {
        for (SBElement<?> sbElement : value.keySet()) {
            if (sbElement.toString().equals(name)) {
                return value.get(sbElement);
            }
        }
        return new VoidElement("Dict 中不存在的键: " + name);
    }
    @ElementMethod(name = "size", alias = {"length"}, returnType = "Int")
    public IntElement method_size(Parser parser, EntrustInst... args) {
        return new IntElement((long) value.size());
    }
    @ElementMethod(name = "get", args = {"Any"}, returnType = "Any")
    public SBElement<?> method_get(Parser parser, EntrustInst... args) {
        SBElement<?> arg1 = args[0].execute(parser);
        if (!value.containsKey(arg1)) {
            return new VoidElement("Dict 中不存在的键: " + arg1);
        }
        return value.get(arg1);
    }
    @ElementMethod(name = "put", args = {"Any", "Any"}, returnType = "Map")
    public DictElement method_set(Parser parser, EntrustInst... args) {
        SBElement<?> arg1 = args[0].execute(parser);
        SBElement<?> arg2 = args[1].execute(parser);
        value.put(arg1, arg2);
        return this;
    }
    @ElementMethod(name = "containsKey", args = {"Any"}, returnType = "Bool")
    public BoolElement method_contains(Parser parser, EntrustInst... args) {
        SBElement<?> arg1 = args[0].execute(parser);
        return BoolElement.fromBool(value.containsKey(arg1));
    }
    @ElementMethod(name = "remove", args = {"Any"}, returnType = "List")
    public DictElement method_remove(Parser parser, EntrustInst... args) {
        SBElement<?> arg1 = args[0].execute(parser);
        value.remove(arg1);
        return this;
    }
    @ElementMethod(name = "keys", returnType = "List")
    public ListElement method_keys(Parser parser, EntrustInst... args) {
        return new ListElement(value.keySet().toArray(new SBElement<?>[0]));
    }
    @ElementMethod(name = "values", returnType = "List")
    public ListElement method_values(Parser parser, EntrustInst... args) {
        return new ListElement(value.values().toArray(new SBElement<?>[0]));
    }
    @ElementMethod(name = "items", returnType = "List")
    public ListElement method_items(Parser parser, EntrustInst... args) {
        ArrayList<ListElement> result = new ArrayList<>();
        for (SBElement<?> key : value.keySet()) {
            result.add(new ListElement(key, value.get(key)));
        }
        return new ListElement(result.toArray(new ListElement[0]));
    }
    @ElementMethod(name = "map", args = {"Entrust"}, returnType = "List")
    public DictElement method_map(Parser parser, EntrustInst... args) {
        String k = "key";
        String v = "value";
        EntrustInst run;
        if (args.length > 1) {
            k = args[0].execute(parser).asString().value;
            v = args[1].execute(parser).asString().value;
            run = args[2];
        } else {
            run = args[0];
        }
        for (SBElement<?> inst : value.keySet()) {
            parser.getVariables().put(k, inst);
            parser.getVariables().put(v, value.get(inst));
            this.put(inst, run.execute(parser));
        }
        return this;
    }
    @ElementMethod(name = "filter", args = {"Entrust"}, returnType = "List")
    public DictElement method_filter(Parser parser, EntrustInst... args) {
        String k = "key";
        String v = "value";
        EntrustInst run;
        if (args.length > 1) {
            k = args[0].execute(parser).asString().value;
            v = args[1].execute(parser).asString().value;
            run = args[2];
        } else {
            run = args[0];
        }
        for (SBElement<?> inst : value.keySet()) {
            parser.getVariables().put(k, inst);
            parser.getVariables().put(v, value.get(inst));
            if (!run.execute(parser).asBool().toBool()) {
                this.value.remove(inst);
            }
        }
        return this;
    }
}
