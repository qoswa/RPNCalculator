package com.company;

import java.io.IOException;
import java.util.EmptyStackException;
import java.util.Stack;

public class Parser {

    // Для преобразования инфиксной нотации в польскую обратную запись используется алгоритм сортировочной станции.
    static String infixToPostfix(String infix) throws IOException,
            EmptyStackException {

        // Строка с операторами служит для определения приоритета операции путем деления индекса оператора на 2
        // ? : = 0 ; + - = 1 ; / * = 2
        final String operators = "?:+-/*";

        StringBuilder sb = new StringBuilder();

        // Стэк для хранения операторов.
        Stack < Integer > stack = new Stack < >();

        String[] tokens = makeTokens(infix);

        for (String token: tokens) {
            char nextChar = token.charAt(0);
            //  idx = индекс оператора в строке операторов, -1 для случаев, когда токен не является оператором
            // 0 обозначает + , 1 обозначает - , 2 обозначает /, 3 обозначает *
            int idx = operators.indexOf(nextChar);

            // token.length()==1 - служит для проверки, дейтсвительно ли в токене оператор "минус", а не отрицательное число

            if (idx != -1 && token.length() == 1) { // Если токен является оператором
                if (stack.empty()) {
                    stack.push(idx);
                } else {
                    while (!stack.empty()) {
                        int precendencePeek = stack.peek() / 2;
                        int precendenceIdx = idx / 2;

                        if (precendencePeek >= precendenceIdx) sb.append(operators.charAt(stack.pop())).append(' ');
                        else break;
                    }
                    stack.push(idx);
                }

            } else if (nextChar == '(') {
                stack.push( - 2); // -2 обозначает "(" в стеке.
            } else if (nextChar == ')') {
                while (stack.peek() != -2)
                    sb.append(operators.charAt(stack.pop())).append(' ');
                stack.pop();
            } else if (nextChar == '?') {
                sb.append(operators.charAt(stack.pop())).append(' ');

                stack.push( - 3);
            } else if (nextChar == ':') {
                while (stack.peek() != -3) {
                    sb.append(operators.charAt(stack.pop())).append(' ');
                }
                stack.pop();
            } else { // Если токен является числом или знаком сравнения
                sb.append(token).append(' ');
            }

        }

        while (!stack.isEmpty())
            sb.append(operators.charAt(stack.pop())).append(' ');

        return sb.toString();

    }

    static String[] makeTokens(String infix) throws IOException {

        String possibleChars = "-/+*01234=><?:5.6\\s789()"; // Строка с допустимыми символами
        if (!infix.matches("[" + possibleChars + "]+")) throw new IOException("Wrong input expression");

        return infix.trim().split("\\s+");
    }
}