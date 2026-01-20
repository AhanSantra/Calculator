package io.github.ahansantra.calculator.history;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

public class HistoryPanel extends JPanel {

    private final DefaultListModel<HistoryEntry> model = new DefaultListModel<>();
    private final HistoryManager manager;

    public HistoryPanel(HistoryManager manager) {
        this.manager = manager;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(180, 0));
        setBackground(new Color(22,22,22));

        JList<HistoryEntry> list = new JList<>(model);
        list.setCellRenderer(new HistoryRenderer());
        list.setBackground(new Color(22,22,22));
        list.setBorder(null);

        JScrollPane sp = new JScrollPane(list);
        sp.setBorder(null);
        sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JButton clear = new JButton("Clear");
        JButton export = new JButton("Export");

        clear.addActionListener(e -> {
            manager.clear();
            model.clear();
        });

        export.addActionListener(e -> {
            try {
                Desktop.getDesktop().open(manager.getFile().getParentFile());
            } catch (Exception ignored) {}
        });

        JPanel top = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        top.setBackground(new Color(22,22,22));
        top.add(export);
        top.add(clear);

        add(top, BorderLayout.NORTH);
        add(sp, BorderLayout.CENTER);

        load();
    }

    public void load() {
        model.clear();
        List<HistoryEntry> entries = manager.load();
        for (HistoryEntry e : entries) model.addElement(e);
    }

    public void addEntry(String expr, String result) {
        HistoryEntry e = new HistoryEntry(expr, result);
        model.add(0, e);
        manager.append(expr, result);
    }
}
