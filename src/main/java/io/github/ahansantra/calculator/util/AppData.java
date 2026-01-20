package io.github.ahansantra.calculator.util;

import java.io.File;

public class AppData {

    public static File getAppDataDir(String appName) {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            return new File(System.getenv("APPDATA"), appName);
        } else {
            return new File(System.getProperty("user.home"),
                    ".local/share/" + appName);
        }
    }
}
