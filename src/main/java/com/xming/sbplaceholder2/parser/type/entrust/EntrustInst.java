package com.xming.sbplaceholder2.parser.type.entrust;

import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBInst;
import com.xming.sbplaceholder2.parser.type.inst.ExpressionInst;
import com.xming.sbplaceholder2.parser.type.inst.StringInst;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.Arrays;

public class EntrustInst {
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
        while (!tasks.isEmpty()) {
            Task task = tasks.remove(0);
            object = switch (task.type()) {
                case CALL_SELF -> object.symbol_call(task.args());
                case CALL_METHOD -> object.symbol_runMethod(task.name(), task.args());
                case GET_FIELD -> object.symbol_getField(task.name());
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
        }
        return object;
    }

    public static SBInst<?> runFunction(SBInst<?> object, String name, SBInst<?>... args) {
        return object.symbol_runMethod(name, args);
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
}
