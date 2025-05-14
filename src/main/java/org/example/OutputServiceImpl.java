package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OutputServiceImpl implements OutputService {
    private final Gson gson;

    public OutputServiceImpl() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public void saveTestResultsToFile(String filePath, Map<String, Object> testResult) {
        String file = (String) testResult.get("file_path");
        String methodName = (String) testResult.get("test_method_name");

        Map<String, Object> entry = new HashMap<>();
        entry.put("commit", testResult.get("commit"));
        entry.put("body", testResult.get("test_body"));

        Map<String, Map<String, List<Map<String, Object>>>> root = new HashMap<>();

        try {
            java.io.File f = new java.io.File(filePath);
            if (f.exists()) {
                try (java.io.Reader reader = new java.io.FileReader(filePath)) {
                    Type type = new TypeToken<Map<String, Map<String, List<Map<String, Object>>>>>() {}.getType();
                    root = new Gson().fromJson(reader, type);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, List<Map<String, Object>>> fileMap = root.getOrDefault(file, new HashMap<>());
        List<Map<String, Object>> methodList = fileMap.getOrDefault(methodName, new ArrayList<>());

        methodList.add(entry);
        fileMap.put(methodName, methodList);
        root.put(file, fileMap);

        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(root, writer);
            System.out.println("Test results saved to: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, Object> formatTestOutput(String methodName, String body, String filePath,
                                                String annotations, int assertionAmount, String assertionType,
                                                List<Integer> assertionLines, String commitID) {
        Map<String, Object> testOutput = new HashMap<>();
        testOutput.put("file_path", filePath);
        testOutput.put("test_method_name", methodName);
        testOutput.put("commit", commitID);
        testOutput.put("test_body", body);
        testOutput.put("annotations", annotations);
        testOutput.put("assertion_amount", assertionAmount);
        testOutput.put("assertion_type", assertionType);
        testOutput.put("assertion_lines", assertionLines);
        return testOutput;
    }

    @Override
    public void saveAllTestResults(String filePath, Map<String, Map<String, List<Map<String, Object>>>> results) {
        try {
            String jsonOutput = gson.toJson(results);
            Files.write(Paths.get(filePath), jsonOutput.getBytes());
            System.out.println("\n Method evolution written to: " + filePath);
        } catch (IOException e) {
            System.err.println("Error saving results: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
