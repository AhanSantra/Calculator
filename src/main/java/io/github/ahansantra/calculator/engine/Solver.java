package io.github.ahansantra.calculator.engine;

import java.util.ArrayList;
import java.util.Stack;

public class Solver {

    private ArrayList<Token> equation;
    private Stack<Double> answer = new Stack<>();
    private boolean radians = true;
    public Solver(ArrayList<Token> equation, boolean radians){
        this.equation = equation;
        this.radians = radians;
    }

    public double solve(){

        for (Token t : equation){

            if (t.getType() == TOKENDATATYPE.NUMBER ||
                    t.getType() == TOKENDATATYPE.CONSTANT) {

                answer.push(t.getNumericValue());
            }
            else {
                int n = t.getArity();
                double[] args = new double[n];

                // pop in reverse order!
                for (int i = n - 1; i >= 0; i--) {
                    args[i] = answer.pop();
                }

                double result = t.apply(radians,args);
                answer.push(result);
            }
        }

        return answer.pop();   // final answer
    }
}
