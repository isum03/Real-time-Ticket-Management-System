import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    //Naming the logfile
    private static final String LOG_FILE = "activity_log.txt";

    public static synchronized void log(String message) {
        // Printing in  CLI
        System.out.println(message);
        // Writing log file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
}
