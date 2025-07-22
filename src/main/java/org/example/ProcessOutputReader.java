package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Purpose: "Reads" output
 */
public class ProcessOutputReader {

    public static String readProcessOutput(Process process) throws IOException {
        StringBuilder output = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            while ((line = errorReader.readLine()) != null) {
                output.append("Error ").append(line).append("\n");
            }
        }
        return output.toString();
    }

    // Read output function
    public static String readProcessOutputWithTimeout(Process process, long timeoutSeconds) throws IOException {
        return readProcessOutput(process);
    }
}
