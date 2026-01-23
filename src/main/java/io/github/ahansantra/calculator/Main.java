package io.github.ahansantra.calculator;

import io.github.ahansantra.calculator.ui.CalculatorUI;
import javax.swing.*;

class Main{
    public static void main(String[] args){
        Updater.check(
                "1.0.0",
                "https://ahansantra.github.io/windows/calculator/latest.json"
        );

        SwingUtilities.invokeLater(() ->
                new CalculatorUI().setVisible(true));
    }
}