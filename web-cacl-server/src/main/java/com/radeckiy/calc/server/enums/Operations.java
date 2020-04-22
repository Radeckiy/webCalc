package com.radeckiy.calc.server.enums;

import com.radeckiy.calc.server.exceptions.DivideByZeroException;

/**
 * Enum с мат. операциями
 */
public enum Operations {
    ADDITION('+') {
        public Double apply(Number op1, Number op2) {
            return op1.doubleValue() + op2.doubleValue();
        }
    },

    SUBTRACTION('-') {
        public Double apply(Number op1, Number op2) {
            return op1.doubleValue() - op2.doubleValue();
        }
    },

    MULTIPLICATION('*') {
        public Double apply(Number op1, Number op2) {
            return op1.doubleValue() * op2.doubleValue();
        }
    },

    DIVISION('/') {
        public Double apply(Number op1, Number op2) {
            if(op2.doubleValue() == 0)
                throw new DivideByZeroException();

            return op1.doubleValue() / op2.doubleValue();
        }
    },

    POWER('^') {
        public Double apply(Number op1, Number op2) {
            if(op2.doubleValue() == 0)
                throw new DivideByZeroException();

            return Math.pow(op1.doubleValue(), op2.doubleValue());
        }
    };

    public abstract Double apply(Number op1, Number op2);

    private char operation;

    Operations(char operation) {
        this.operation = operation;
    }

    public char getOperation() {
        return operation;
    }
}
