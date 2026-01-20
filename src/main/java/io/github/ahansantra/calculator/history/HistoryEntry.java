package io.github.ahansantra.calculator.history;

public class HistoryEntry {
    public final String expression;
    public final String result;

    public HistoryEntry(String expression, String result) {
        this.expression = expression;
        this.result = result;
    }

    @Override
    public String toString() {
        return expression + " = " + result;
    }
}
