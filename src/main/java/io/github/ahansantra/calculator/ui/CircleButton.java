package io.github.ahansantra.calculator.ui;

import javax.swing.*;
import java.awt.*;

public class CircleButton extends JButton {

    private float textScale = 1.0f; // 👈 text size control

    public CircleButton(String text){
        super(text);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
    }

    // 🔧 Control text size
    public void setTextScale(float scale) {
        this.textScale = scale;
        updateFont();
    }

    private void updateFont() {
        Font f = getFont();
        if (f != null) {
            setFont(f.deriveFont(f.getSize2D() * textScale));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int d = Math.min(getWidth(), getHeight());
        int x = (getWidth() - d) / 2;
        int y = (getHeight() - d) / 2;

        g2.setColor(getBackground());
        g2.fillOval(x, y, d, d);

        super.paintComponent(g);
    }

    @Override
    public boolean contains(int x, int y) {
        int r = Math.min(getWidth(), getHeight()) / 2;
        int cx = getWidth() / 2;
        int cy = getHeight() / 2;
        return Math.pow(x - cx, 2) + Math.pow(y - cy, 2) <= r * r;
    }
}
