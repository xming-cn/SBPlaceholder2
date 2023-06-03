package com.xming.sbplaceholder2.exception;

import com.xming.sbplaceholder2.parser.type.SBInst;

public class UnsupportedOperationException extends RuntimeException {
    public <T extends SBInst<?>> UnsupportedOperationException(T obj, T other, String operator) {
        super("operator " + operator + " is not supported for " + obj.toDebug() + " and " + other.toDebug());
    }
}
