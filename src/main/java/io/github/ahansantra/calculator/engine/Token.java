package io.github.ahansantra.calculator.solve;

public class Token {

    // The actual text of the token (e.g. "123", "+", "sin", "(" )
    private String data;

    // What kind of token it is
    private TOKENDATATYPE type;

    public Token(String data, TOKENDATATYPE type) {
        this.data = data;
        this.type = type;
    }

    // ===== Getters =====
    public String getData() {
        return data;
    }

    public TOKENDATATYPE getType() {
        return type;
    }

    // ===== Setters =====
    public void setData(String data) {
        this.data = data;
    }

    public void setType(TOKENDATATYPE type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Token[" + type + " : " + data + "]";
    }
}
