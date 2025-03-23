package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OutputServiceImpl implements OutputService {
    private final Gson gson;

    public OutputServiceImpl() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public void saveTestResultsToFile(String filePath, Map<String, Object> testResults) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            gson.toJson(testResults, writer);
            writer.write(",\n"); // Add comma for valid JSON array structure
            System.out.println("Test results saved to: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, Object> formatTestOutput(String methodName, String body, String filePath,
                                                String annotations, int assertionAmount, String assertionType, List<Integer> assertionLines, String refactorings) {
        Map<String, Object> testOutput = new HashMap<>();
        testOutput.put("file_path", filePath);
        testOutput.put("test_method_name", methodName);
        testOutput.put("test_body", body);
        testOutput.put("annotations", annotations);
        testOutput.put("assertion_amount", assertionAmount);
        testOutput.put("assertion_type", assertionType);
        testOutput.put("assertion_lines", assertionLines);
        testOutput.put("refactorings", refactorings);
        return testOutput;
    }
}
