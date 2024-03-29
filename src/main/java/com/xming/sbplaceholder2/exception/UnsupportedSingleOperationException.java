package com.xming.sbplaceholder2.exception;

import com.xming.sbplaceholder2.parser.type.SBElement;

public class UnsupportedSingleOperationException extends RuntimeException {
    public <T extends SBElement<?>> UnsupportedSingleOperationException(T obj, String operator) {
        super("operator " + operator + " is not supported for " + obj.toDebug());
    }
}
