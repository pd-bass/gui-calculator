package de.sebastianheuckmann.controller;

public enum Operation {
    NONE(0), ADD(1), SUBTRACT(1), MULTIPLY(2), DIVIDE(2), POWER(3), ROOT(3);

    private final int precedence;
    Operation(int precedence){
        this.precedence = precedence;
    }
    public int getPrecedence() {
        return precedence;
    }
}
