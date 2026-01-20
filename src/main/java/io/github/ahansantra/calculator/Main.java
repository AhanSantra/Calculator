package io.github.ahansantra.calculator;

import io.github.ahansantra.calculator.ui.CalculatorUI;

import javax.swing.*;

class Main{
    public static void main(String[] args){
        SwingUtilities.invokeLater(() ->
                new CalculatorUI().setVisible(true));
    }
}