package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Output {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void saveTestResultsToFile(String filePath, Map<String, Object> testResults) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            gson.toJson(testResults, writer);
            System.out.println("Test results saved to: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Object> formatTestOutput(String methodName, String body, String filePath) {
        Map<String, Object> testOutput = new HashMap<>();
        testOutput.put("test_method_name", methodName);
        testOutput.put("test_body", body);
        testOutput.put("file_path", filePath);
        return testOutput;
    }
}
