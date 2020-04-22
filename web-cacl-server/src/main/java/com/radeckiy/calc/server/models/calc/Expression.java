package com.radeckiy.calc.server.models.calc;

import com.radeckiy.calc.server.enums.Operations;
import com.radeckiy.calc.server.interfaces.ExecutableExpression;

public class Expression implements ExecutableExpression {
    private String expression;
    private Operations operation;

    public Expression(String expression) {
        StringBuilder exp = new StringBuilder(expression);

        if(expression.charAt(0) == '(' && exp.indexOf(")") == exp.length() - 1) {
            exp.deleteCharAt(0);
            exp.deleteCharAt(exp.length() - 1);
        }

        this.expression = exp.toString();
    }

    /**
     * Функция работает по алгоритму:
     * Если expression - простое число - отдаем результат как есть
     * Иначе - если expression - выражение, то определяем для него
     * самую приоритетную операцию, записываем её в operation, и
     * создаём 2 вложенных Expression (левое и правое), записав в
     * них всё что было слева от операции и, соотвественно, справа
     *
     * @return - результат расчёта всех вложенных Expression
     */
    @Override
    public double execute() {
        // Если выражение - просто число - отдаём как есть
        if(isDigit(expression))
            return Double.parseDouble(expression);

        // Иначе
        else {
            // Получаем самую приоритетную операцию
            int indexOp = getIndexMaxPriorityOperation(expression);

            for(Operations operation : Operations.values()) {
                if(operation.getOperation() == expression.charAt(indexOp)) {
                    this.operation = operation;
                    break;
                }
            }

            // Вытаскиваем всё что до знака операции и создаём новый экземпляр Expression из этого
            ExecutableExpression leftExpression = new Expression(expression.substring(0, indexOp));
            // Вытаскиваем всё что после знака операции и создаём новый экземпляр Expression из этого
            ExecutableExpression rightExpression = new Expression(expression.substring(indexOp + 1));

            // Исполняем эту операцию
            // В качестве первого операнда - результат вызова execute() левого выражения, в качестве второго - результат правого
            return operation.apply(leftExpression.execute(), rightExpression.execute());
        }
    }

    // [+], [-] => 1, [*], [/] => 2, [^] => 3
    private int getIndexMaxPriorityOperation(String expression) {
        StringBuilder exp = new StringBuilder(expression);

        while (exp.indexOf("(") != -1) {
            int x = exp.indexOf("(");
            exp.delete(x, exp.indexOf(")", x) + 1);
        }

        if(exp.indexOf("+") != -1 || exp.indexOf("-") != -1)
            return Math.max(expression.lastIndexOf("+"), expression.lastIndexOf("-"));
        else if(exp.indexOf("/") != -1 || exp.indexOf("*") != -1)
            return Math.max(expression.lastIndexOf("/"), expression.lastIndexOf("*"));
        else  if(exp.indexOf("^") != -1)
            return expression.lastIndexOf("^");
        return 0;
    }

    /**
     * Функция для определения принадлежности к числам
     * @param str - строка с символами
     * @return - если является числом - true, иначе - false
     */
    private boolean isDigit(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
