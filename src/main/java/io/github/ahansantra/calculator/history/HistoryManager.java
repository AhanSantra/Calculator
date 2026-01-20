package io.github.ahansantra.calculator.history;

import io.github.ahansantra.calculator.util.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryManager {

    private final File file;

    public HistoryManager() {
        File dir = AppData.getAppDataDir("RealmeCalculator");
        if (!dir.exists()) dir.mkdirs();
        file = new File(dir, "history.csv");
        try { file.createNewFile(); } catch (IOException ignored) {}
    }

    public void append(String expr, String result) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            bw.write(expr.replace(",", " ") + "," + result);
            bw.newLine();
        } catch (IOException ignored) {}
    }

    public List<HistoryEntry> load() {
        List<HistoryEntry> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String l;
            while ((l = br.readLine()) != null) {
                String[] p = l.split(",", 2);
                if (p.length == 2)
                    list.add(new HistoryEntry(p[0], p[1]));
            }
        } catch (IOException ignored) {}
        return list;
    }

    public void clear() {
        try (FileWriter fw = new FileWriter(file)) {
            fw.write("");
        } catch (IOException ignored) {}
    }

    public File getFile() {
        return file;
    }
}
