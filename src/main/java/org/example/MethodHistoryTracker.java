/*package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MethodHistoryTracker {

    public static class MethodHistory {
        String methodSignature;
        List<Version> versions = new ArrayList<>();
        String refactorings; // Refactoring details from RefactoringMiner

        public static class Version {
            String commitId;
            String changeType;
            String body;
            String assertionType; // Type of assertions in the method
            List<Integer> assertionLines; // Line numbers of assertions

            public Version(String commitId, String changeType, String body,
                           String assertionType, List<Integer> assertionLines) {
                this.commitId = commitId;
                this.changeType = changeType;
                this.body = body;
                this.assertionType = assertionType;
                this.assertionLines = assertionLines;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MethodHistory that = (MethodHistory) o;
            return Objects.equals(methodSignature, that.methodSignature);
        }

        @Override
        public int hashCode() {
            return Objects.hash(methodSignature);
        }
    }

    private final Map<String, MethodHistory> methodHistories = new ConcurrentHashMap<>();
    private final Map<String, String> lastBodies = new ConcurrentHashMap<>();
    private final String outputPath;
    private final Gson gson;

    public MethodHistoryTracker(String outputPath) {
        this.outputPath = outputPath;
        this.gson = new GsonBuilder().setPrettyPrinting().create();

        // Load existing method histories if the file exists
        File file = new File(outputPath);
        if (file.exists() && file.length() > 0) {
            try (FileReader reader = new FileReader(file)) {
                Type listType = new TypeToken<List<MethodHistory>>(){}.getType();
                List<MethodHistory> histories = gson.fromJson(reader, listType);

                if (histories != null) {
                    for (MethodHistory history : histories) {
                        methodHistories.put(history.methodSignature, history);

                        // Update lastBodies with the most recent version
                        if (!history.versions.isEmpty()) {
                            MethodHistory.Version lastVersion = history.versions.get(history.versions.size() - 1);
                            if (!"REMOVED".equals(lastVersion.changeType)) {
                                lastBodies.put(history.methodSignature, lastVersion.body);
                            }
                        }
                    }
                    System.out.println("Loaded " + methodHistories.size() + " method histories from existing file");
                }
            } catch (IOException e) {
                System.err.println("Error loading existing method histories: " + e.getMessage());
            }
        }
    }

    public synchronized void addMethodVersion(String signature, String commitId, String changeType,
                                              String body, String assertionType, List<Integer> assertionLines) {
        MethodHistory history = methodHistories.computeIfAbsent(signature, k -> {
            MethodHistory h = new MethodHistory();
            h.methodSignature = k;
            return h;
        });

        // Check if this exact version already exists
        for (MethodHistory.Version version : history.versions) {
            if (version.commitId.equals(commitId) &&
                    version.changeType.equals(changeType) &&
                    Objects.equals(version.body, body)) {
                // Skip duplicates
                return;
            }
        }

        history.versions.add(new MethodHistory.Version(commitId, changeType, body, assertionType, assertionLines));

        if (body != null) {
            lastBodies.put(signature, body);
        } else if (changeType.equals("REMOVED")) {
            lastBodies.remove(signature);
        }

        // Update JSON file immediately
        saveHistoryToJson();
    }

    public void setRefactorings(String signature, String refactorings) {
        MethodHistory history = methodHistories.get(signature);
        if (history != null) {
            history.refactorings = refactorings;
            saveHistoryToJson();
        }
    }

    public boolean methodChanged(String signature, String currentBody) {
        return !lastBodies.containsKey(signature) || !lastBodies.get(signature).equals(currentBody);
    }

    public boolean isMethodKnown(String signature) {
        return lastBodies.containsKey(signature);
    }

    public Set<String> getKnownMethods() {
        return new HashSet<>(lastBodies.keySet());
    }

    private synchronized void saveHistoryToJson() {
        try (FileWriter writer = new FileWriter(outputPath)) {
            gson.toJson(methodHistories.values(), writer);
        } catch (IOException e) {
            System.err.println("Error writing method history to JSON: " + e.getMessage());
        }
    }
}


 */
