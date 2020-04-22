package com.radeckiy.calc.server;

import com.radeckiy.calc.server.models.calc.Expression;

public class CalcMain {
    // Точка запуска самого калькулятора для тестирования
    /*public static void main(String[] args) {
        System.out.println(calc("(2+2)/4-1"));
    }*/

    /**
     * Метод, который вызывается удалённо через RPC
     * @param expression - выражением
     * @return - ответ
     */
    public static double calc(String expression) {
        return new Expression(parseExpression(expression)).execute();
    }

    /**
     * Функция удаляет все лишние символы из строки с выражением
     * (по ъорошему надо проверять скобки, наличие более одной точки
     * в числе и прочие более замудрённые проверки)
     * @param expression - исходная строка с выражением
     * @return - отфильтрованная строка с выражением
     */
    private static String parseExpression(String expression) {
        StringBuilder result = new StringBuilder();

        for(char c : expression.toCharArray()) {
            if(Character.isDigit(c) || isOperator(c) || c == '.')
                result.append(c);
        }
        return result.toString();
    }

    /**
     * Функция определяет, принадлежит ли символ к операторам ( +, -, /, *, ^, (, ) )
     * @param c - символ
     * @return - true, если да, false - нет
     */
    private static boolean isOperator(char c) {
        return ("+-/*^()".indexOf(c) != -1);
    }
}