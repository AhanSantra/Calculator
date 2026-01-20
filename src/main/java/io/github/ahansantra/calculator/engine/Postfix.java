package io.github.ahansantra.calculator.engine;

import java.util.ArrayList;
import java.util.Stack;

public class Postfix {

    private final Token[] equation;
    private final ArrayList<Token> output = new ArrayList<>();
    private final Stack<Token> stack = new Stack<>();

    public Postfix(Token[] equation) {
        this.equation = equation;
    }

    public void convert() {

        for (Token token : equation) {

            if (token.getType() == TOKENDATATYPE.NUMBER ||
                    token.getType() == TOKENDATATYPE.CONSTANT) {

                output.add(token);
            }

            else if (token.isFunction()) {
                stack.push(token);
            }

            else if (token.isOperator()) {

                while (!stack.isEmpty()
                        && (stack.peek().isOperator() || stack.peek().isFunction())
                        && stack.peek().getPrecedence() >= token.getPrecedence()) {

                    output.add(stack.pop());
                }
                stack.push(token);
            }

            else if (token.isLeftParen()) {
                stack.push(token);
            }

            else if (token.isRightParen()) {

                while (!stack.isEmpty() && !stack.peek().isLeftParen()) {
                    output.add(stack.pop());
                }

                if (!stack.isEmpty()) stack.pop(); // remove "("

                if (!stack.isEmpty() && stack.peek().isFunction()) {
                    output.add(stack.pop());
                }
            }
        }

        while (!stack.isEmpty()) {
            output.add(stack.pop());
        }
    }

    public ArrayList<Token> getOutput() {
        return output;
    }
}
