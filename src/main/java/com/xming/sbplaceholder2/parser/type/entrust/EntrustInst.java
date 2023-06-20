package com.xming.sbplaceholder2.parser.type.entrust;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.event.FastElementBuildEvent;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBElement;
import com.xming.sbplaceholder2.parser.type.TypeManager;
import com.xming.sbplaceholder2.parser.type.inst.ExpressionElement;
import com.xming.sbplaceholder2.parser.type.inst.StringElement;
import com.xming.sbplaceholder2.parser.type.type.TypeType;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.Arrays;

public class EntrustInst implements Cloneable {
    SBElement<?> object;
    ArrayList<Task> tasks = null;
    public EntrustInst() {}
    public EntrustInst(SBElement<?> object, Task... task) {
        this.object = object;
        if (task != null) {
            this.tasks = new ArrayList<>();
            this.tasks.addAll(Arrays.asList(task));
        }
    }
    public void addTask(Task task) {
        if (this.tasks == null) tasks = new ArrayList<>();
        tasks.add(task);
    }
    public SBElement<?> execute(Parser parser) {
        return execute(parser, parser.getPlayer().value);
    }
    public SBElement<?> execute(Parser parser, OfflinePlayer player) {
        if (tasks == null) return object;
        SBElement<?> object;
        try {
            object = (SBElement<?>) this.object.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
        ArrayList<Task> tasks = new ArrayList<>();
        for (Task task : this.tasks) {
            tasks.add((Task) task.clone());
        }
        while (!tasks.isEmpty()) {
            Task task = tasks.remove(0);
            if (parser.depth < parser.debug) {
                SBPlaceholder2.logger.info("┃ ".repeat(parser.depth) + "┏  处理 " + object.toDebug() + " 的 " + task + " 委托");
            }
            parser.depth++;
            object = switch (task.type()) {
                case CALL_SELF -> object.symbol_call(parser, task.args());
                case CALL_METHOD -> {
                    TypeManager.SBMethod method = TypeManager.getInstance().getMethod(object, task.name());
                    if (method == null) {
                        throw new RuntimeException("Method not found: " + task.name());
                    }
                    yield method.trigger(parser, object, task.args());
                }
                case GET_FIELD -> object.symbol_getField(parser, task.name());
                case SUB_EXPRESSION -> {
                    if (object instanceof ExpressionElement expression) {
                        yield expression.parse(parser);
                    } else {
                        throw new RuntimeException("SUB_EXPRESSION entrust can't cast on " + object.getClass().getName());
                    }
                }
                case PARSE_VARIABLE -> {
                    if (object instanceof StringElement inst) {
                        if (parser.getVariables().containsKey(inst.value)) {
                            yield parser.getVariables().get(inst.value);
                        } else if (Parser.getGlobal_variables().containsKey(inst.value)) {
                            yield Parser.getGlobal_variables().get(inst.value);
                        } else if (inst.value.startsWith("{") && inst.value.endsWith("}")) {
                            yield new StringElement(PlaceholderAPI.setPlaceholders(
                                    player, "%" + inst.value.substring(1, inst.value.length() - 1) + "%"));
                        } else if (TypeManager.getInstance().getTypes().contains(inst.value)) {
                            yield TypeType.inst.newInst(inst.asString().value);
                        } else {
                            FastElementBuildEvent event = new FastElementBuildEvent(parser, inst.value);
                            Bukkit.getPluginManager().callEvent(event);
                            if (event.getResult() == null)      yield object;
                            else                                yield event.getResult();
                        }
                    } else {
                        yield object;
                    }
                }
            };
            parser.depth--;
            if (parser.depth < parser.debug) {
                SBPlaceholder2.logger.info("┃ ".repeat(parser.depth) + "┗  委托完成" + ": " + object.toDebug());
            }
        }
        return object;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(object.toDebug());
        if (tasks == null) return result.toString();
        for (Task task : tasks) {
            result.append(" -> ").append(task.toString());
        }
        return result.toString();
    }

    public void setObject(SBElement<?> object) {
        this.object = object;
    }

    @Override
    public Object clone() {
        try {
            EntrustInst inst = (EntrustInst) super.clone();
            inst.object = (SBElement<?>) object.clone();
            if (tasks == null) return inst;
            inst.tasks = new ArrayList<>();
            for (Task task : tasks) {
                inst.tasks.add((Task) task.clone());
            }
            return inst;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Clone failed");
        }
    }
}
