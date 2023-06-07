package com.xming.sbplaceholder2.parser.type.inst;

import com.google.common.base.Joiner;
import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.InstMethod;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBInst;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
import com.xming.sbplaceholder2.parser.type.type.DictType;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class DictInst extends SBInst<DictType> {
    HashMap<SBInst<?>, SBInst<?>> value = new HashMap<>();
    public DictInst(SBInst<?>... insts) {
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
        return "List";
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
    public void put(SBInst<?> key, SBInst<?> value) {
        this.value.put(key, value);
    }
    @Override
    public SBInst<?> symbol_getField(Parser parser, String name) {
        for (SBInst<?> sbInst : value.keySet()) {
            if (sbInst.toString().equals(name)) {
                return value.get(sbInst);
            }
        }
        return VoidInst.instance;
    }
    @InstMethod(name = "size", alias = {"length"}, returnType = "Int")
    public IntInst method_size(Parser parser, EntrustInst... args) {
        return new IntInst(value.size());
    }
    @InstMethod(name = "get", args = {"Any"}, returnType = "Any")
    public SBInst<?> method_get(Parser parser, EntrustInst... args) {
        PlayerInst player = parser.getPlayer();
        SBInst<?> arg1 = args[0].execute(parser, player.value);
        if (!value.containsKey(arg1)) {
            return VoidInst.instance;
        }
        return value.get(arg1);
    }
    @InstMethod(name = "put", args = {"Any", "Any"}, returnType = "Map")
    public DictInst method_set(Parser parser, EntrustInst... args) {
        PlayerInst player = parser.getPlayer();
        SBInst<?> arg1 = args[0].execute(parser, player.value);
        SBInst<?> arg2 = args[1].execute(parser, player.value);
        value.put(arg1, arg2);
        return this;
    }
    @InstMethod(name = "keys", returnType = "List")
    public ListInst method_keys(Parser parser, EntrustInst... args) {
        return new ListInst(value.keySet().toArray(new SBInst<?>[0]));
    }
    @InstMethod(name = "values", returnType = "List")
    public ListInst method_values(Parser parser, EntrustInst... args) {
        return new ListInst(value.values().toArray(new SBInst<?>[0]));
    }
    @InstMethod(name = "items", returnType = "List")
    public ListInst method_items(Parser parser, EntrustInst... args) {
        ArrayList<ListInst> result = new ArrayList<>();
        for (SBInst<?> key : value.keySet()) {
            result.add(new ListInst(key, value.get(key)));
        }
        return new ListInst(result.toArray(new ListInst[0]));
    }
    @InstMethod(name = "map", args = {"Entrust"}, returnType = "List")
    public DictInst method_map(Parser parser, EntrustInst... args) {
        PlayerInst player = parser.getPlayer();
        String k = "key";
        String v = "value";
        EntrustInst run;
        if (args.length > 1) {
            k = args[0].execute(parser, player.value).asString().value;
            v = args[1].execute(parser, player.value).asString().value;
            run = args[2];
        } else {
            run = args[0];
        }
        for (SBInst<?> inst : value.keySet()) {
            parser.getVariables().put(k, inst);
            parser.getVariables().put(v, value.get(inst));
            this.put(inst, run.execute(parser, player.value));
        }
        return this;
    }
    @InstMethod(name = "filter", args = {"Entrust"}, returnType = "List")
    public DictInst method_filter(Parser parser, EntrustInst... args) {
        PlayerInst player = parser.getPlayer();
        String k = "key";
        String v = "value";
        EntrustInst run;
        if (args.length > 1) {
            k = args[0].execute(parser, player.value).asString().value;
            v = args[1].execute(parser, player.value).asString().value;
            run = args[2];
        } else {
            run = args[0];
        }
        for (SBInst<?> inst : value.keySet()) {
            parser.getVariables().put(k, inst);
            parser.getVariables().put(v, value.get(inst));
            if (!run.execute(parser, player.value).asBool().toBool()) {
                this.value.remove(inst);
            }
        }
        return this;
    }
    @InstMethod(name = "remove", args = {"Any"}, returnType = "List")
    public DictInst method_remove(Parser parser, EntrustInst... args) {
        PlayerInst player = parser.getPlayer();
        SBInst<?> arg1 = args[0].execute(parser, player.value);
        value.remove(arg1);
        return this;
    }
    @InstMethod(name = "containsKey", args = {"Any"}, returnType = "Bool")
    public BoolInst method_contains(Parser parser, EntrustInst... args) {
        PlayerInst player = parser.getPlayer();
        SBInst<?> arg1 = args[0].execute(parser, player.value);
        return BoolInst.fromBool(value.containsKey(arg1));
    }
}
