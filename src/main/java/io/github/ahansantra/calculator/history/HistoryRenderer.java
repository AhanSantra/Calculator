package io.github.ahansantra.calculator.history;

import javax.swing.*;
import java.awt.*;

public class HistoryRenderer extends JLabel implements ListCellRenderer<HistoryEntry> {

    public HistoryRenderer() {
        setOpaque(true);
        setFont(new Font("Segoe UI", Font.PLAIN, 13));
        setBorder(BorderFactory.createEmptyBorder(6,6,6,6));
    }

    @Override
    public Component getListCellRendererComponent(
            JList<? extends HistoryEntry> list,
            HistoryEntry value, int index,
            boolean isSelected, boolean cellHasFocus) {

        setText("<html><b>" + value.expression +
                "</b><br><span style='color:#aaaaaa'>" +
                value.result + "</span></html>");

        setBackground(isSelected ? new Color(40,40,40) : new Color(22,22,22));
        setForeground(Color.WHITE);
        return this;
    }
}
