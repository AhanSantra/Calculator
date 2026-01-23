public class Updater {

    public static void check(String version, String metaUrl) {

        if (!System.getProperty("os.name").startsWith("Windows")) return;

        try {
            String json = new String(
                    new URL(metaUrl).openStream().readAllBytes()
            );

            String latest = json.split("\"version\":\"")[1].split("\"")[0];
            String exeUrl = json.split("\"url\":\"")[1].split("\"")[0];

            if (version.equals(latest)) return;

            Path newExe = Paths.get("calculator-new.exe");

            try (InputStream in = new URL(exeUrl).openStream()) {
                Files.copy(in, newExe, StandardCopyOption.REPLACE_EXISTING);
            }

            new ProcessBuilder(
                    "cmd", "/c",
                    "timeout /t 1 & move /y calculator-new.exe calculator.exe & start calculator.exe"
            ).start();

            System.exit(0);

        } catch (Exception ignored) {}
    }
}
