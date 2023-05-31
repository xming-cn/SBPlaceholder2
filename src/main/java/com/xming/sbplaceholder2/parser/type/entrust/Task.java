package com.xming.sbplaceholder2.parser.type.entrust;

import com.xming.sbplaceholder2.parser.type.SBInst;
import org.jetbrains.annotations.Nullable;

public class Task {
    public final TaskType type;
    @Nullable public final String name;
    @Nullable public final SBInst<?>[] args;
    public Task(TaskType type, @Nullable String name, @Nullable SBInst<?>... args) {
        this.type = type;
        this.name = name;
        this.args = args;
    }
    enum TaskType {
        CALL_SELF,
        CALL_METHOD,
        GET_FIELD
    }
}
