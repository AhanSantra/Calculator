package io.github.ahansantra.calculator.ui;

import io.github.ahansantra.calculator.engine.*;
import io.github.ahansantra.calculator.history.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CalculatorUI extends JFrame {

    // ================= DISPLAY =================
    private JTextField display;
    private JScrollPane displayScroll;

    // ================= KEYPADS =================
    private JPanel keypadHolder;
    private JPanel basicPad;
    private JPanel scientificPad;
    private boolean scientific = false;

    // ================= HISTORY =================
    private HistoryPanel historyPanel;
    private boolean historyVisible = false;

    // ================= ENGINE =================
    private final List<Token> tokens = new ArrayList<>();
    private boolean radians = true;
    private boolean inverse = false;

    private int openBrackets = 0;
    private int closeBrackets = 0;

    // ================= BUTTONS =================
    private CircleButton invBtn;
    private CircleButton radBtn;
    private CircleButton sinBtn;
    private CircleButton cosBtn;
    private CircleButton tanBtn;

    // ================= ICON LOADER =================
    private ImageIcon loadIcon(String path, int w, int h) {
        try {
            Image img = new ImageIcon(getClass().getResource(path)).getImage();
            return new ImageIcon(img.getScaledInstance(w, h, Image.SCALE_SMOOTH));
        } catch (Exception e) {
            return new ImageIcon();
        }
    }

    // ================= CONSTRUCTOR =================
    public CalculatorUI() {
        setTitle("Realme Calculator");
        setSize(350, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(18,18,18));

        // ---- HISTORY (ADD ONLY) ----
        HistoryManager historyManager = new HistoryManager();
        historyPanel = new HistoryPanel(historyManager);
        historyPanel.setVisible(false);
        add(historyPanel, BorderLayout.EAST);

        add(createTopBar(), BorderLayout.NORTH);
        add(createCenterPanel(), BorderLayout.CENTER);
        add(createKeypadHolder(), BorderLayout.SOUTH);
    }

    // ================= TOP BAR =================
    private JPanel createTopBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(new Color(20,20,20));
        bar.setBorder(new EmptyBorder(6,10,6,10));

        RippleButton historyBtn =
                new RippleButton(loadIcon("/icons/history.png",20,20));
        historyBtn.setBackground(new Color(20,20,20));
        historyBtn.addActionListener(e -> toggleHistory());

        RippleButton menu = new RippleButton("⋮");
        menu.setFont(new Font("Segoe UI", Font.BOLD, 22));
        menu.setForeground(Color.WHITE);
        menu.setBackground(new Color(20,20,20));

        bar.add(historyBtn, BorderLayout.WEST);
        bar.add(menu, BorderLayout.EAST);
        return bar;
    }

    // ================= CENTER =================
    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(18,18,18));
        panel.setBorder(new EmptyBorder(20,20,10,20));

        display = new JTextField("0");
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("Segoe UI", Font.BOLD, 38));
        display.setForeground(Color.WHITE);
        display.setBackground(new Color(18,18,18));
        display.setBorder(null);

        displayScroll = new JScrollPane(display);
        displayScroll.setBorder(null);
        displayScroll.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        displayScroll.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        panel.add(displayScroll, BorderLayout.CENTER);

        RippleButton swap =
                new RippleButton(loadIcon("/icons/swap.png",24,24));
        swap.setBackground(new Color(18,18,18));
        swap.addActionListener(e -> toggleScientific());

        JPanel row = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        row.setBackground(new Color(18,18,18));
        row.add(swap);

        panel.add(row, BorderLayout.SOUTH);
        return panel;
    }

    // ================= KEYPADS =================
    private JPanel createKeypadHolder() {
        keypadHolder = new JPanel(new BorderLayout());
        keypadHolder.setBackground(new Color(18,18,18));

        basicPad = createBasicPad();
        scientificPad = createScientificPad();

        keypadHolder.add(basicPad, BorderLayout.CENTER);
        return keypadHolder;
    }

    private JPanel createBasicPad() {
        JPanel grid = new JPanel(new GridLayout(5,4,5,10));
        grid.setBorder(new EmptyBorder(12,12,18,12));
        grid.setBackground(new Color(18,18,18));

        String[] keys = {
                "AC","()","%","÷",
                "7","8","9","×",
                "4","5","6","-",
                "1","2","3","+",
                "0",".","⌫","="
        };

        for(String k : keys) grid.add(makeButton(k));
        return grid;
    }

    private JPanel createScientificPad() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(18, 18, 18));

        JPanel sci = new JPanel(new GridLayout(3, 4, 10, 10));
        sci.setBorder(new EmptyBorder(12, 12, 0, 12));
        sci.setBackground(new Color(18, 18, 18));

        String[] sciKeys = {
                "√", "π", "^", "!",
                "RAD", "sin", "cos", "tan",
                "INV", "e", "ln", "log"
        };

        for (String k : sciKeys) {
            CircleButton b = makeButton(k);
            if (k.equals("INV")) invBtn = b;
            if (k.equals("RAD")) radBtn = b;
            if (k.equals("sin")) sinBtn = b;
            if (k.equals("cos")) cosBtn = b;
            if (k.equals("tan")) tanBtn = b;
            sci.add(b);
        }

        root.add(sci, BorderLayout.NORTH);
        root.add(createBasicPad(), BorderLayout.CENTER);
        return root;
    }

    // ================= BUTTON FACTORY =================
    private CircleButton makeButton(String text){
        CircleButton btn = new CircleButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setForeground(Color.WHITE);

        if("=".equals(text))
            btn.setBackground(new Color(20,227,91));
        else if("÷×-+^!()%√πRADsincostanINVelnlog".contains(text))
            btn.setBackground(new Color(13,154,228));
        else if("AC".equals(text))
            btn.setBackground(new Color(5,80,166));
        else
            btn.setBackground(new Color(40,40,40));

        btn.addActionListener(e -> handleInput(text));
        return btn;
    }

    // ================= INPUT =================
    private void handleInput(String k){

        if(k.matches("[0-9]")) inputNumber(k);
        else if(k.equals(".")) inputDecimal();
        else if(k.equals("π") || k.equals("e")) inputConstant(k);
        else if(k.equals("sin") || k.equals("cos") || k.equals("tan"))
            inputTrig(k);
        else if(k.equals("ln") || k.equals("log"))
            inputLog(k);
        else if("+-×÷^".contains(k))
            inputOperator(k);
        else if(k.equals("%"))
            inputPercent();
        else if(k.equals("⌫"))
            backspace();
        else if(k.equals("AC")){
            tokens.clear();
            openBrackets = closeBrackets = 0;
        }
        else if(k.equals("=")){
            evaluate();
            return;
        }
        else if(k.equals("RAD")){
            radians = !radians;
            radBtn.setText(radians ? "RAD" : "DEG");
        }
        else if(k.equals("INV")){
            inverse = !inverse;
            sinBtn.setText(inverse ? "sin⁻¹" : "sin");
            cosBtn.setText(inverse ? "cos⁻¹" : "cos");
            tanBtn.setText(inverse ? "tan⁻¹" : "tan");

            sinBtn.setTextScale(inverse ? 0.7f : 1f);
            cosBtn.setTextScale(inverse ? 0.7f : 1f);
            tanBtn.setTextScale(inverse ? 0.7f : 1f);
        }
        else if(k.equals("()")){
            if(tokens.isEmpty() ||
                    tokens.get(tokens.size()-1).getType()==TOKENDATATYPE.SYMBOL){
                tokens.add(new Token(TOKENDATATYPE.SYMBOL,"("));
                openBrackets++;
            } else {
                if(openBrackets > closeBrackets){
                    tokens.add(new Token(TOKENDATATYPE.SYMBOL,")"));
                    closeBrackets++;
                } else {
                    tokens.add(new Token(TOKENDATATYPE.SYMBOL,"("));
                    openBrackets++;
                }
            }
        }

        refreshDisplay();
    }

    // ================= SMART INPUT =================
    private void inputNumber(String d){
        if(tokens.isEmpty() ||
                tokens.get(tokens.size()-1).getType()!=TOKENDATATYPE.NUMBER)
            tokens.add(new Token(TOKENDATATYPE.NUMBER,d));
        else
            tokens.get(tokens.size()-1)
                    .setData(tokens.get(tokens.size()-1).getData()+d);
    }

    private void inputDecimal(){
        if(tokens.isEmpty()){
            tokens.add(new Token(TOKENDATATYPE.NUMBER,"0."));
            return;
        }
        Token t = tokens.get(tokens.size()-1);
        if(t.getType()==TOKENDATATYPE.NUMBER && !t.getData().contains("."))
            t.setData(t.getData()+".");
    }

    private void inputOperator(String op){
        if(tokens.isEmpty()) return;

        Token last = tokens.get(tokens.size()-1);
        String real = op.equals("×") ? "*" : op.equals("÷") ? "/" : op;

        if(last.getType()==TOKENDATATYPE.SYMBOL &&
                "+-*/^".contains(last.getData())){
            last.setData(real);
        } else {
            tokens.add(new Token(TOKENDATATYPE.SYMBOL, real));
        }
    }

    private void inputTrig(String fn){
        String engine = inverse ? "a"+fn : fn;
        tokens.add(new Token(TOKENDATATYPE.SYMBOL,engine));
        tokens.add(new Token(TOKENDATATYPE.SYMBOL,"("));
        openBrackets++;
    }

    private void inputLog(String fn){
        tokens.add(new Token(TOKENDATATYPE.SYMBOL,fn));
        tokens.add(new Token(TOKENDATATYPE.SYMBOL,"("));
        openBrackets++;
    }

    private void inputConstant(String c){
        tokens.add(new Token(TOKENDATATYPE.CONSTANT,c));
    }

    private void inputPercent(){
        if(tokens.isEmpty()) return;
        tokens.add(new Token(TOKENDATATYPE.SYMBOL,"/"));
        tokens.add(new Token(TOKENDATATYPE.NUMBER,"100"));
    }

    private void backspace(){
        if(tokens.isEmpty()) return;

        Token t = tokens.get(tokens.size()-1);
        if(t.getType()==TOKENDATATYPE.NUMBER && t.getData().length()>1){
            t.setData(t.getData().substring(0,t.getData().length()-1));
        } else {
            tokens.remove(tokens.size()-1);
        }
    }

    // ================= EVAL =================
    private void evaluate(){
        try{
            Token[] infix = tokens.toArray(new Token[0]);
            Postfix p = new Postfix(infix);
            p.convert();

            Solver s = new Solver(p.getOutput(), radians);
            double r = s.solve();

            String expr = display.getText();
            String result = formatResult(r);

            display.setText(result);
            historyPanel.addEntry(expr, result);

            tokens.clear();
            tokens.add(new Token(TOKENDATATYPE.NUMBER, result));

        }catch(Exception e){
            display.setText("Error");
            tokens.clear();
        }
        scrollToEnd();
    }

    // ================= DISPLAY =================
    private void refreshDisplay(){
        if(tokens.isEmpty()){
            display.setText("0");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for(Token t: tokens){
            sb.append(
                    t.getData().equals("*") ? "×" :
                            t.getData().equals("/") ? "÷" :
                                    t.getData()
            );
        }
        display.setText(sb.toString());
        scrollToEnd();
    }

    // ================= SCROLL =================
    private void scrollToEnd(){
        SwingUtilities.invokeLater(() ->
                display.setCaretPosition(
                        display.getDocument().getLength()));
    }

    // ================= HISTORY =================
    private void toggleHistory(){
        historyVisible = !historyVisible;
        historyPanel.setVisible(historyVisible);
        revalidate();
        repaint();
    }

    // ================= FORMAT =================
    private String formatResult(double v){
        BigDecimal bd = BigDecimal.valueOf(v).stripTrailingZeros();
        return bd.compareTo(BigDecimal.ZERO)==0 ? "0" : bd.toPlainString();
    }

    private void toggleScientific() {
        keypadHolder.removeAll();

        if (scientific) {
            keypadHolder.add(basicPad, BorderLayout.CENTER);
        } else {
            keypadHolder.add(scientificPad, BorderLayout.CENTER);
        }

        scientific = !scientific;

        keypadHolder.revalidate();
        keypadHolder.repaint();
    }
}
