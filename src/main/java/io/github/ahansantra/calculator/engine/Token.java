package io.github.ahansantra.calculator.engine;

public class Token {

    private TOKENDATATYPE type;
    private String data;

    public Token(TOKENDATATYPE type, String data) {
        this.type = type;
        this.data = data;
    }

    public TOKENDATATYPE getType() {
        return type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    // ================= CLASSIFICATION =================

    public boolean isFunction() {
        if (type != TOKENDATATYPE.SYMBOL) return false;

        switch (data) {
            case "sin":
            case "cos":
            case "tan":
            case "asin":
            case "acos":
            case "atan":
            case "ln":
            case "log":
            case "√":
                return true;
        }
        return false;
    }

    public boolean isOperator() {
        if (type != TOKENDATATYPE.SYMBOL) return false;
        if (data.equals("(") || data.equals(")")) return false;
        return !isFunction();
    }

    public boolean isLeftParen() {
        return data.equals("(");
    }

    public boolean isRightParen() {
        return data.equals(")");
    }

    // ================= PRECEDENCE =================

    public int getPrecedence() {

        if (isFunction()) return 5;

        switch (data) {
            case "!": return 4;
            case "^": return 3;
            case "*":
            case "/":
            case "%": return 2;
            case "+":
            case "-": return 1;
        }
        return -1;
    }

    // ================= ARITY =================

    public int getArity() {

        if (isFunction()) return 1;

        switch (data) {
            case "+":
            case "-":
            case "*":
            case "/":
            case "^":
                return 2;
            case "!":
            case "%":
                return 1;
        }
        return 0;
    }

    // ================= APPLY =================

    public double apply(boolean radians,double... args) {

        switch (data) {

            case "+": return args[0] + args[1];
            case "-": return args[0] - args[1];
            case "*": return args[0] * args[1];
            case "/": return args[0] / args[1];
            case "^": return Math.pow(args[0], args[1]);
            case "%": return (float)args[0] / 100;
            case "sin": return Math.sin(radians ? args[0] : Math.toRadians(args[0]));
            case "cos": return Math.cos(radians ? args[0] : Math.toRadians(args[0]));
            case "tan": return Math.tan(radians ? args[0] : Math.toRadians(args[0]));
            case "asin": return radians ? Math.asin(args[0]) : Math.toDegrees(Math.asin(args[0]));
            case "acos": return radians ? Math.acos(args[0]) : Math.toDegrees(Math.acos(args[0]));
            case "atan": return radians ? Math.atan(args[0]) : Math.toDegrees(Math.atan(args[0]));
            case "ln": return Math.log(args[0]);
            case "log": return Math.log10(args[0]);
            case "√": return Math.sqrt(args[0]);

            case "!":
                int n = (int) args[0];
                if (n < 0) throw new ArithmeticException("Negative factorial");
                double f = 1;
                for (int i = 2; i <= n; i++) f *= i;
                return f;
        }

        throw new RuntimeException("Unknown operator: " + data);
    }

    // ================= NUMERIC =================

    public double getNumericValue() {

        if (type == TOKENDATATYPE.NUMBER)
            return Double.parseDouble(data);

        if (type == TOKENDATATYPE.CONSTANT) {
            if (data.equals("π")) return Math.PI;
            if (data.equals("e")) return Math.E;
        }

        throw new RuntimeException("Not numeric: " + data);
    }

    @Override
    public String toString() {
        return data;
    }
}
