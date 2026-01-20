package io.github.ahansantra.calculator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculatorUI extends JFrame {

    private JTextField display;

    public CalculatorUI() {
        setTitle("Realme Calculator");
        setSize(420,760);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(18,18,18));

        add(createTopBar(), BorderLayout.NORTH);
        add(createCenterPanel(), BorderLayout.CENTER);
        add(createButtonGrid(), BorderLayout.SOUTH);
    }

    // ================= TOP BAR =================
    private JPanel createTopBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(new Color(20,20,20));
        bar.setBorder(new EmptyBorder(6,10,6,10));

        RippleButton historyBtn = new RippleButton(loadIcon("/icons/history.png",22,22));
        historyBtn.setBackground(new Color(20,20,20));
        historyBtn.addActionListener(e -> {
            // TODO history
        });

        RippleButton menuBtn = new RippleButton("⋮");
        menuBtn.setFont(new Font("Segoe UI", Font.BOLD, 22));
        menuBtn.setForeground(Color.WHITE);
        menuBtn.setBackground(new Color(20,20,20));

        bar.add(historyBtn, BorderLayout.WEST);
        bar.add(menuBtn, BorderLayout.EAST);
        return bar;
    }

    // ================= CENTER =================
    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(18,18,18));
        panel.setBorder(new EmptyBorder(20,20,20,20));

        display = new JTextField("0");
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("Segoe UI", Font.BOLD, 42));
        display.setForeground(Color.WHITE);
        display.setBackground(new Color(18,18,18));
        display.setBorder(null);

        panel.add(display, BorderLayout.CENTER);

        RippleButton imgBtn = new RippleButton(loadIcon("/icons/swap.png",26,26));
        imgBtn.setBackground(new Color(18,18,18));

        JPanel row = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        row.setBackground(new Color(18,18,18));
        row.add(imgBtn);
        panel.add(row, BorderLayout.SOUTH);

        return panel;
    }

    // ================= BUTTON GRID =================
    private JPanel createButtonGrid() {
        JPanel grid = new JPanel(new GridLayout(5,4,10,10));
        grid.setBackground(new Color(18,18,18));
        grid.setBorder(new EmptyBorder(12,12,18,12));

        String[] keys = {
                "AC","()","%","÷",
                "7","8","9","×",
                "4","5","6","-",
                "1","2","3","+",
                "0",".","⌫","="
        };

        for(String k : keys){
            CircleButton btn = new CircleButton(k);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 20));
            btn.setForeground(Color.WHITE);

            if("=".contains(k))
                btn.setBackground(new Color(20, 227, 91, 255));
            else if("()%÷×-+".contains(k))
                    btn.setBackground(new Color(13, 154, 228, 255));
            else if("AC".equals(k))
                    btn.setBackground(new Color(5, 80, 166,255));
            else
                btn.setBackground(new Color(40,40,40));

            btn.addActionListener(e -> {
                // TODO calculator logic
            });

            grid.add(btn);
        }
        return grid;
    }

    // ================= DISPLAY API =================
    public String getDisplay(){ return display.getText(); }
    public void setDisplay(String s){ display.setText(s); }

    // ================= ICON LOADER =================
    private ImageIcon loadIcon(String path,int w,int h){
        Image img = new ImageIcon(getClass().getResource(path)).getImage();
        return new ImageIcon(img.getScaledInstance(w,h,Image.SCALE_SMOOTH));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalculatorUI().setVisible(true));
    }
}
