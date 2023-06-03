package com.xming.sbplaceholder2.parser.type.entrust;

import com.xming.sbplaceholder2.SBPlaceholder2;
import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBInst;
import com.xming.sbplaceholder2.parser.type.TypeManager;
import com.xming.sbplaceholder2.parser.type.inst.ExpressionInst;
import com.xming.sbplaceholder2.parser.type.inst.StringInst;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.Arrays;

public class EntrustInst implements Cloneable {
    SBInst<?> object;
    ArrayList<Task> tasks = null;
    public EntrustInst() {}
    public EntrustInst(SBInst<?> object, Task... task) {
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
    public SBInst<?> execute(Parser parser, OfflinePlayer player) {
        if (tasks == null) return object;
        SBInst<?> object;
        try {
            object = (SBInst<?>) this.object.clone();
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
                case CALL_SELF -> object.symbol_call(parser, task.executeArgs(parser, player));
                case CALL_METHOD -> {
                    TypeManager.SBMethod method = TypeManager.getInstance().getMethod(object, task.name());
                    yield method.trigger(parser, object, task.args());
                }
                case GET_FIELD -> object.symbol_getField(parser, task.name());
                case SUB_EXPRESSION -> ((ExpressionInst) object).parse(parser, player);
                case PARSE_VARIABLE -> {
                    if (object instanceof StringInst inst) {
                        if (parser.getVariables().containsKey(inst.value)) {
                            yield parser.getVariables().get(inst.value);
                        } else {
                            // TODO: 2021/8/3
                            // kotlin like string template
                            yield object;
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

    public void setObject(SBInst<?> object) {
        this.object = object;
    }

    @Override
    public Object clone() {
        try {
            EntrustInst inst = (EntrustInst) super.clone();
            inst.object = (SBInst<?>) object.clone();
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
