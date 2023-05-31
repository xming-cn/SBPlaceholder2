package com.xming.sbplaceholder2.parser.type.entrust;

import com.xming.sbplaceholder2.parser.type.SBInst;

import java.util.ArrayList;
import java.util.Arrays;

public class EntrustInst {
    SBInst<?> object;
    ArrayList<Task> tasks = null;
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
    public SBInst<?> execute() {
        if (tasks == null) return object;
        while (!tasks.isEmpty()) {
            Task task = tasks.remove(0);
            object = switch (task.type) {
                case CALL_SELF -> object.symbol_call(task.args);
                case CALL_METHOD -> object.symbol_runMethod(task.name, task.args);
                case GET_FIELD -> object.symbol_getField(task.name);
            };
        }
        return object;
    }

    public static SBInst<?> runFunction(SBInst<?> object, String name, SBInst<?>... args) {
        return object.symbol_runMethod(name, args);
    }
}
