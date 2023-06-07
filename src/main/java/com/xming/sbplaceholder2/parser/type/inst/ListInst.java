package com.xming.sbplaceholder2.parser.type.inst;

import com.google.common.base.Joiner;
import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.InstMethod;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBInst;
import com.xming.sbplaceholder2.parser.type.entrust.EntrustInst;
import com.xming.sbplaceholder2.parser.type.type.ListType;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;

public class ListInst extends SBInst<ListType> {
    ArrayList<SBInst<?>> value = new ArrayList<>();
    public ListInst(SBInst<?>... insts) {
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
        return "[" + Joiner.on(", ").join(value.stream().map(SBInst::toString).toArray(String[]::new)) + "]";
    }
    @Override
    public String toDebug() {
        if (value.size() < 6) {
            return "List@[" + Joiner.on(", ").join(value.stream().map(SBInst::toDebug).toArray(String[]::new)) + "]";
        } else {
            return "List@[" + value.get(0).toDebug() + ", " + value.get(1).toDebug() + ", ...(" + (value.size() - 3) + "), " + value.get(value.size() - 1).toDebug() + " ]";
        }
    }
    public void addAll(SBInst<?>... insts) {
        value.addAll(Arrays.asList(insts));
    }
    public void append(SBInst<?> inst) {
        value.add(inst);
    }
    @Override
    public SBInst<?> symbol_mul(SBInst<?> other) {
        ListInst result = new ListInst();
        for (int i = 0; i < other.asInt().value; i++) {
            result.addAll(value.toArray(new SBInst<?>[0]));
        }
        return super.symbol_mul(other);
    }
    @Override
    public SBInst<?> symbol_getField(Parser parser, String name) {
        return value.get(Integer.parseInt(name));
    }
    @InstMethod(name = "size", alias = {"length"}, returnType = "Int")
    public IntInst method_size(Parser parser, EntrustInst... args) {
        return new IntInst(value.size());
    }
    @InstMethod(name = "get", args = {"Int"}, returnType = "Any")
    public SBInst<?> method_get(Parser parser, EntrustInst... args) {
        PlayerInst player = parser.getPlayer();
        IntInst arg1 = args[0].execute(parser, player.value).asInt();
        return value.get(arg1.value);
    }
    @InstMethod(name = "set", args = {"Int", "Any"}, returnType = "List")
    public ListInst method_set(Parser parser, EntrustInst... args) {
        PlayerInst player = parser.getPlayer();
        IntInst arg1 = args[0].execute(parser, player.value).asInt();
        SBInst<?> args2 = args[1].execute(parser, player.value);
        value.set(arg1.value, args2);
        return this;
    }
    @InstMethod(name = "join", args = {"String"}, returnType = "String")
    public StringInst method_join(Parser parser, EntrustInst... args) {
        PlayerInst player = parser.getPlayer();
        StringInst arg1 = args[0].execute(parser, player.value).asString();
        return new StringInst(String.join(arg1.value, value.stream().map(SBInst::toString).toArray(String[]::new)));
    }
    @InstMethod(name = "append", args = {"Any"}, returnType = "List")
    public ListInst method_append(Parser parser, EntrustInst... args) {
        PlayerInst player = parser.getPlayer();
        SBInst<?> args1 = args[0].execute(parser, player.value);
        value.add(args1);
        return this;
    }
    @InstMethod(name = "map", args = {"Entrust"}, returnType = "List")
    public ListInst method_map(Parser parser, EntrustInst... args) {
        PlayerInst player = parser.getPlayer();
        String each;
        EntrustInst run;
        if (args.length > 1) {
            each = args[0].execute(parser, player.value).asString().value;
            run = args[1];
        } else {
            each = "it";
            run = args[0];
        }
        ListInst result = new ListInst();
        for (SBInst<?> inst : value) {
            parser.getVariables().put(each, inst);
            result.append(run.execute(parser, player.value));
        }
        return result;
    }
    @InstMethod(name = "filter", args = {"Entrust"}, returnType = "List")
    public ListInst method_filter(Parser parser, EntrustInst... args) {
        PlayerInst player = parser.getPlayer();
        String each;
        EntrustInst run;
        if (args.length > 1) {
            each = args[0].execute(parser, player.value).asString().value;
            run = args[1];
        } else {
            each = "it";
            run = args[0];
        }
        ListInst result = new ListInst();
        for (SBInst<?> inst : value) {
            parser.getVariables().put(each, inst);
            if (run.execute(parser, player.value).asBool().toBool()) {
                result.append(inst);
            }
        }
        return result;
    }
    @InstMethod(name = "sort", returnType = "List")
    public ListInst method_sort(Parser parser, EntrustInst... args) {
        value.sort(SBInst::symbol_compare);
        return this;
    }
    @InstMethod(name = "reverse", returnType = "List")
    public ListInst method_reverse(Parser parser, EntrustInst... args) {
        ArrayList<SBInst<?>> result = new ArrayList<>();
        for (int i = value.size() - 1; i >= 0; i--) {
            result.add(value.get(i));
        }
        return new ListInst(result.toArray(new SBInst<?>[0]));
    }
    @InstMethod(name = "remove", args = {"Int"}, returnType = "List")
    public ListInst method_remove(Parser parser, EntrustInst... args) {
        PlayerInst player = parser.getPlayer();
        IntInst arg1 = args[0].execute(parser, player.value).asInt();
        value.remove((int) arg1.value);
        return this;
    }
    @InstMethod(name = "insert", args = {"Int", "Any"}, returnType = "List")
    public ListInst method_insert(Parser parser, EntrustInst... args) {
        PlayerInst player = parser.getPlayer();
        IntInst arg1 = args[0].execute(parser, player.value).asInt();
        SBInst<?> args2 = args[1].execute(parser, player.value);
        value.add(arg1.value, args2);
        return this;
    }
    @InstMethod(name = "contains", args = {"Any"}, returnType = "Bool")
    public BoolInst method_contains(Parser parser, EntrustInst... args) {
        PlayerInst player = parser.getPlayer();
        SBInst<?> arg1 = args[0].execute(parser, player.value);
        return BoolInst.fromBool(value.contains(arg1));
    }
    @InstMethod(name = "index", args = {"Any"}, returnType = "Int")
    public IntInst method_index(Parser parser, EntrustInst... args) {
        PlayerInst player = parser.getPlayer();
        SBInst<?> arg1 = args[0].execute(parser, player.value);
        return new IntInst(value.indexOf(arg1));
    }
    @InstMethod(name = "lastIndex", args = {"Any"}, returnType = "Int")
    public IntInst method_lastIndex(Parser parser, EntrustInst... args) {
        PlayerInst player = parser.getPlayer();
        SBInst<?> arg1 = args[0].execute(parser, player.value);
        return new IntInst(value.lastIndexOf(arg1));
    }
    @InstMethod(name = "random", returnType = "Any")
    public SBInst<?> method_random(Parser parser, EntrustInst... args) {
        return value.get((int) (Math.random() * value.size()));
    }
}
