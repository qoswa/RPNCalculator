package com.company;

import java.io.IOException;
import java.util. * ;

public class RPNCalc {

    public static void main(String[] args)  {

        new GUI();

    }

    public static double calculateExpr(String[] postfixTokens) throws EmptyStackException {

        RPNCalc calc = new RPNCalc();

        Stack < Double > stack = new Stack < >();

        MathOperation addition = (a,b) ->stack.push(b + a);
        MathOperation substraction = (a,b) ->stack.push(b - a);
        MathOperation multiplication = (a,b) ->stack.push(b * a);
        MathOperation divison = (a,b) -> {
            stack.push(b / a);
            if(a==0){
                throw new IllegalArgumentException();
            }
        };

        int tokenIndex = 0;

        for (String token: postfixTokens) {

            double value1, value2, trueRes, falseRes;
            Double number;

            try {
                number = Double.parseDouble(token);
                stack.push(number);
            } catch(NumberFormatException e) {}

            switch (token) {

                case "*":
                    calc.operate(stack.pop(), stack.pop(), multiplication);
                    break;
                case "/":
                    calc.operate(stack.pop(), stack.pop(), divison);
                    break;
                case "-":
                    calc.operate(stack.pop(), stack.pop(), substraction);
                    break;
                case "+":
                    calc.operate(stack.pop(), stack.pop(), addition);
                    break;
                case ":":
                    // Тернарный оператор.
                    int signIndex = findSignIndex(postfixTokens, tokenIndex);

                    falseRes = stack.pop(); // результат, если булевого выражение ложное
                    trueRes = stack.pop(); // результат, если булевого выражение верное
                    value2 = stack.pop(); // левая часть булевого выражения в тернарном операторе
                    value1 = stack.pop(); // правая часть булевого выражения в тернарном операторе.

                    if (postfixTokens[signIndex].equals(">")) {
                        double temp = (value1 > value2) ? stack.push(trueRes) : stack.push(falseRes);
                    } else if (postfixTokens[signIndex].equals("<")) {
                        double temp = (value1 < value2) ? stack.push(trueRes) : stack.push(falseRes);
                    } else {
                        double temp = (value1 == value2) ? stack.push(trueRes) : stack.push(falseRes);
                    }

                    break;
            }

            tokenIndex++;
        }

        return stack.pop();

    }

    // Метод для нахождения знака сравнения для актуального ":" .
    private static int findSignIndex(String[] postfixTokens, int tokenIndex) {
        int colonSignCounter = 0;
        for (int i = 0; i < tokenIndex; i++) {
            if (postfixTokens[i].equals(":")) {
                colonSignCounter++;
            }
        }
        int signCounter = 0;
        for (int i = tokenIndex; i >= 0; i--) {
            if (postfixTokens[i].equals(">") || postfixTokens[i].equals("<") || postfixTokens[i].equals("=")) {
                signCounter++;
                if (colonSignCounter == (signCounter - 1)) {
                    return i;
                }
            }
        }
        return - 1;
    }

    interface MathOperation {
        void operation(double a, double b);
    }

    private void operate(double a, double b, MathOperation mathOperation) {
        mathOperation.operation(a, b);
    }

}

