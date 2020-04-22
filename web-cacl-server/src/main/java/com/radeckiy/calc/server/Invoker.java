package com.radeckiy.calc.server;

import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Очень странный класс
 */
public class Invoker {
    private Class targetClass;
    private PrintStream errorsPrintStream;

    public Invoker(Class targetClass) {
        this.targetClass = targetClass;
        this.errorsPrintStream = System.err;
    }

    public Invoker(Class targetClass, PrintStream errorsPrintStream) {
        this.targetClass = targetClass;
        this.errorsPrintStream = errorsPrintStream;
    }

    /**
     * Ищет метод с указанным названием и аргументами и если находит - исполняет
     * @param methodName - название функции
     * @param args - аргументы для функции
     * @return - результат исполнения функции
     */
    public Object invokeMethod(String methodName, Object... args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        for(Method m : targetClass.getDeclaredMethods()) {
            if(m.getName().equals(methodName) && m.getParameterCount() == args.length) {
                try {
                    return m.invoke(m, args);
                } catch (IllegalAccessException ex) {
                    errorsPrintStream.println(ex.getLocalizedMessage() + "\n");
                    throw ex;
                } catch (InvocationTargetException ex) {
                    errorsPrintStream.println("Метод с именем '" + methodName + "' Выбросил следующую ошибку:");
                    ex.printStackTrace(errorsPrintStream);
                    throw ex;
                }
            }
        }

        NoSuchMethodException ex = new NoSuchMethodException("Метода с указанными аргументами и именем '" + methodName + "' не существует!\n");
        errorsPrintStream.println(ex.getMessage() + "\n");
        throw ex;
    }
}
