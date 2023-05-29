package com.xming.sbplaceholder2.exception;

import com.xming.sbplaceholder2.parser.type.SBInst;

public class UnsupportedTypeCastException extends RuntimeException{
    public <T extends SBInst<?>> UnsupportedTypeCastException(T obj, String type) {
        super("Cannot cast '" + obj.getName() + "' to '" + type + "'");
    }
}
