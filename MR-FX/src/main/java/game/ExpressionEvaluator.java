package game;

import java.util.*;

public class ExpressionEvaluator {

    public static int evaluate(String expression) {
        List<String> postfix = infixToPostfix(expression);
        return evaluatePostfix(postfix);
    }

    private static List<String> infixToPostfix(String expr) {
        List<String> output = new ArrayList<>();
        Stack<String> ops = new Stack<>();

        StringTokenizer tokenizer = new StringTokenizer(expr, "+-*/() ", true);

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();

            if (token.isEmpty()) continue;

            if (isNumber(token)) {
                output.add(token);
            } else if (isOperator(token)) {
                while (!ops.isEmpty() && precedence(ops.peek()) >= precedence(token)) {
                    output.add(ops.pop());
                }
                ops.push(token);
            } else if (token.equals("(")) {
                ops.push(token);
            } else if (token.equals(")")) {
                while (!ops.isEmpty() && !ops.peek().equals("(")) {
                    output.add(ops.pop());
                }
                if (!ops.isEmpty()) ops.pop(); // pop "("
            }
        }

        while (!ops.isEmpty()) {
            output.add(ops.pop());
        }

        return output;
    }

    private static int evaluatePostfix(List<String> postfix) {
        Stack<Integer> stack = new Stack<>();
        for (String token : postfix) {
            if (isNumber(token)) {
                stack.push(Integer.parseInt(token));
            } else if (isOperator(token)) {
                int b = stack.pop();
                int a = stack.pop();
                switch (token) {
                    case "+" -> stack.push(a + b);
                    case "-" -> stack.push(a - b);
                    case "*" -> stack.push(a * b);
                    case "/" -> stack.push(a / b);
                }
            }
        }
        return stack.pop();
    }

    private static boolean isNumber(String token) {
        return token.matches("-?\\d+");
    }

    private static boolean isOperator(String token) {
        return "+-*/".contains(token);
    }

    private static int precedence(String op) {
        return switch (op) {
            case "+", "-" -> 1;
            case "*", "/" -> 2;
            default -> 0;
        };
    }
}
