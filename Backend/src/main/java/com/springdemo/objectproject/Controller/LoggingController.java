package com.springdemo.objectproject.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
public class LoggingController {

    private PrintStream originalOut = System.out;  // Save the original System.out

    // Start logging when this method is called
    public void startLogging() throws IOException {
        // Create a custom log file to start logging
        Files.createDirectories(Paths.get("logs"));

        // Create a PrintStream to write logs to a file
        PrintStream logStream = new PrintStream(new FileOutputStream("logs/application.log", true));  // 'true' for append mode

        // Redirect System.out to the log file, also log the original output to the file
        System.setOut(new PrintStream(new LogOutputStream(logStream), true)); // true to enable auto-flushing
    }

    // Stop logging by restoring the original System.out
    public void stopLogging() {
        System.setOut(originalOut); // Restore original System.out
        System.out.println("Logging stopped at: " + System.currentTimeMillis());
    }

    // Retrieve logs via API
    @GetMapping("/api/logs")
    public List<String> getLogs() throws IOException {
        String logFilePath = "logs/application.log";
        // Ensure log file exists
        if (!Files.exists(Paths.get(logFilePath))) {
            throw new IOException("Log file not found");
        }
        return Files.readAllLines(Paths.get(logFilePath));
    }

    // Nested class to capture System.out and log it into the PrintStream
    private class LogOutputStream extends java.io.OutputStream {
        private final PrintStream logStream;

        public LogOutputStream(PrintStream logStream) {
            this.logStream = logStream;
        }

        @Override
        public void write(int b) throws IOException {
            logStream.write(b);  // Write the byte to the log stream (file)
            originalOut.write(b); // Also write it to the original System.out (console)
        }

        @Override
        public void write(byte[] b) throws IOException {
            logStream.write(b);  // Write the byte array to the log stream (file)
            originalOut.write(b); // Also write it to the original System.out (console)
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            logStream.write(b, off, len);  // Write the byte array to the log stream (file)
            originalOut.write(b, off, len); // Also write it to the original System.out (console)
        }
    }
}
