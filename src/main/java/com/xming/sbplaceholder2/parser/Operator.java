package com.xming.sbplaceholder2.parser;

public enum Operator {
//    static String[] symbols = {"+", "-", "*", "/", "%", ">=", "<=", ">", "<", "==", "!=", "&&", "||", "!"};
    ADD("+", 2, 1),
    SUB("-", 2, 1),
    MUL("*", 2, 2),
    DIV("/", 2, 2),
    MOD("%", 2, 2),
    GE(">=", 2, 0),
    LE("<=", 2, 0),
    GT(">", 2, 0),
    LT("<", 2, 0),
    EQ("==", 2, 0),
    NE("!=", 2, 0),
    AND("&&", 2, 0),
    OR("||", 2, 0),
    NOT("!", 1, 0),
    CALL("()", 1, 4);

    final String symbol;
    final int element_count;
    final int priority;

    Operator(String symbol, int element_count, int priority) {
        this.symbol = symbol;
        this.priority = priority;
        this.element_count = element_count;
    }
}
