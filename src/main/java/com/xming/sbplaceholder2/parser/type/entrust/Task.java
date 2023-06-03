package com.xming.sbplaceholder2.parser.type.entrust;

import com.xming.sbplaceholder2.parser.Parser;
import com.xming.sbplaceholder2.parser.type.SBInst;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.OfflinePlayer;
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

    public SBInst<?>[] executeArgs(Parser parser, OfflinePlayer player) {
        if (args == null) return new SBInst<?>[0];
        SBInst<?>[] result = new SBInst<?>[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] != null) {
                result[i] = args[i].execute(parser, player);
            }
        }
        return result;
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
