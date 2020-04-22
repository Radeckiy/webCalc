package com.radeckiy.calc.server.exceptions;

public class DivideByZeroException extends ArithmeticException {
    public DivideByZeroException() {
        super("Деление на ноль!");
    }
}
