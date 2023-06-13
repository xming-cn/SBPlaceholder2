package com.xming.sbplaceholder2.parser.type.entrust;

import org.apache.commons.lang.ArrayUtils;
import org.jetbrains.annotations.Nullable;

public record Task(TaskType type, @Nullable String name, @Nullable EntrustInst... args) {
    enum TaskType {
        CALL_SELF,
        CALL_METHOD,
        GET_FIELD,
        PARSE_VARIABLE,
        SUB_EXPRESSION
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(type.name());
        if (name != null) result.append(" ").append(name);
        if (!ArrayUtils.isEmpty(args)) {
            result.append("(");
            for (int i = 0; i < args.length; i++) {
                if (i != 0) result.append(", ");
                if (args[i] != null) {
                    result.append(args[i]);
                }
            }
            result.append(")");
        }
        return result.toString();
    }

    @Override
    protected Object clone() {
        EntrustInst[] args = new EntrustInst[this.args.length];
        for (int i = 0; i < args.length; i++) {
            if (this.args[i] != null) {
                args[i] = (EntrustInst) this.args[i].clone();
            }
        }
        return new Task(type, name, args);
    }
}
