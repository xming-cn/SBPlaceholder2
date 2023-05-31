package com.xming.sbplaceholder2.parser.type.entrust;

import com.xming.sbplaceholder2.parser.type.inst.ExpressionInst;
import org.jetbrains.annotations.Nullable;

public record Task(TaskType type, @Nullable String name, @Nullable ExpressionInst... args) {
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
        if (args != null) {
            result.append("(");
            for (int i = 0; i < args.length; i++) {
                if (i != 0) result.append(", ");
                if (args[i] != null) {
                    result.append(args[i].toDebug());
                }
            }
            result.append(")");
        }
        return result.toString();
    }
}
