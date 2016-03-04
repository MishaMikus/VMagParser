package vmag;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;

public class Log {
    static Date startDate = new Date();
    static String fullLog = "";

    public static void info(Object msg) {
        fullLog += msg + "\n";
        System.out.println(msg);
    }

    public static void info() {
        fullLog += "\n";
        System.out.println();
    }

    public static void saveToFile(String pathToFile) throws IOException {
        String header = "===== START  ===== " + (startDate) + " =====";
        String footer = "===== FINISH ===== " + (new Date()) + " =====";
        fullLog = "\n" + header + "\n" + fullLog + "\n" + footer;
        Files.write(Paths.get(pathToFile), fullLog.getBytes(), new File(pathToFile).exists() ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);
    }
}
