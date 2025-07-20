package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import spoon.Launcher;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.visitor.filter.TypeFilter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class TestMethodAnalyzer {
    private final Gson gson;

    public static class MethodDiff {
        String changeType;
        String refactoringType;
        String methodNameBefore;
        String methodNameAfter;
        String diffSummary;
        List<String> assertionChanges;
        List<String> importChanges;
        List<String> logicChanges;
        boolean hasSignatureChange;
        Map<String, Object> metadata;

        public MethodDiff() {
            this.assertionChanges = new ArrayList<>();
            this.importChanges = new ArrayList<>();
            this.logicChanges = new ArrayList<>();
            this.metadata = new HashMap<>();
        }
    }

    public static class MethodChunk {
        String chunkId;
        String filePath;
        String methodName;
        String beforeCommit;
        String afterCommit;

        // Before state
        Map<String, Object> beforeState;

        // After state
        Map<String, Object> afterState;

        // Changes
        MethodDiff changes;

        // Metadata for RAG
        Map<String, Object> metadata;

        public MethodChunk() {
            this.beforeState = new HashMap<>();
            this.afterState = new HashMap<>();
            this.metadata = new HashMap<>();
        }
    }

    public TestMethodAnalyzer() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     * Analyze method evolution
     */
    public void analyzeAndCreateChunks(String inputFilePath) {
        String baseOutputPath = inputFilePath.replace(".json", "");

        try {
            System.out.println("\n=== Starting Automated Diff Analysis ===");

            // Read existing data
            Map<String, Map<String, List<Map<String, Object>>>> existingData = readExistingData(inputFilePath);

            // Analyze diffs for each method
            Map<String, Map<String, List<Map<String, Object>>>> enhancedData = new HashMap<>();
            List<MethodChunk> chunks = new ArrayList<>();

            int totalMethods = 0;
            int methodsWithChanges = 0;

            for (Map.Entry<String, Map<String, List<Map<String, Object>>>> fileEntry : existingData.entrySet()) {
                String filePath = fileEntry.getKey();
                Map<String, List<Map<String, Object>>> methods = fileEntry.getValue();

                System.out.println("Analyzing file: " + filePath);

                Map<String, List<Map<String, Object>>> enhancedMethods = new HashMap<>();

                for (Map.Entry<String, List<Map<String, Object>>> methodEntry : methods.entrySet()) {
                    String methodName = methodEntry.getKey();
                    List<Map<String, Object>> commits = methodEntry.getValue();

                    totalMethods++;

                    List<Map<String, Object>> enhancedCommits = analyzeMethodCommits(
                            filePath, methodName, commits, chunks
                    );

                    // Check if this method had any changes
                    boolean hasChanges = enhancedCommits.stream()
                            .anyMatch(commit -> commit.containsKey("diff_from_previous"));

                    if (hasChanges) {
                        methodsWithChanges++;
                    }

                    enhancedMethods.put(methodName, enhancedCommits);
                }

                enhancedData.put(filePath, enhancedMethods);
            }

            // Save results
            String enhancedOutputPath = baseOutputPath + "_with_diffs.json";
            String chunksOutputPath = baseOutputPath + "_chunks.json";

            saveEnhancedData(enhancedOutputPath, enhancedData);
            saveChunks(chunksOutputPath, chunks);

            // Print summary at the very end
            System.out.println("\n=== Diff Analysis Complete ===");
            System.out.println("Total methods analyzed: " + totalMethods);
            System.out.println("Methods with changes: " + methodsWithChanges);
            System.out.println("Chunks created: " + chunks.size());
            System.out.println("Enhanced data: " + enhancedOutputPath);
            System.out.println("Chunks: " + chunksOutputPath);

        } catch (Exception e) {
            System.err.println("Error in automated diff analysis: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Analyze commits for a single method
     * */
    private List<Map<String, Object>> analyzeMethodCommits(String filePath, String methodName,
                                                           List<Map<String, Object>> commits, List<MethodChunk> chunks) {

        List<Map<String, Object>> enhancedCommits = new ArrayList<>();

        for (int i = 0; i < commits.size(); i++) {
            Map<String, Object> commit = new HashMap<>(commits.get(i));

            // Add diff analysis for consecutive commits
            if (i > 0) {
                Map<String, Object> previousCommit = commits.get(i - 1);

                try {
                    MethodDiff diff = analyzeMethodDiff(previousCommit, commit);
                    commit.put("diff_from_previous", diff);

                    // Create chunk if there are meaningful changes
                    if (hasSignificantChanges(diff)) {
                        MethodChunk chunk = createMethodChunk(filePath, methodName, previousCommit, commit, diff);
                        chunks.add(chunk);

                        System.out.println("  Found " + diff.refactoringType + " in " + methodName +
                                " (" + previousCommit.get("commit") + " -> " + commit.get("commit") + ")");
                    }
                } catch (Exception e) {
                    System.err.println("  Warning: Failed to analyze diff for " + methodName + ": " + e.getMessage());
                    // Continue processing other commits
                }
            }

            enhancedCommits.add(commit);
        }

        return enhancedCommits;
    }

    /**
     * Core diff analysis with better error handling
     */
    private MethodDiff analyzeMethodDiff(Map<String, Object> before, Map<String, Object> after) {
        MethodDiff diff = new MethodDiff();

        String beforeBody = (String) before.get("body");
        String afterBody = (String) after.get("body");

        // Quick check for identical methods
        if (beforeBody != null && beforeBody.equals(afterBody)) {
            diff.changeType = "no_change";
            diff.refactoringType = "NO_CHANGE";
            diff.diffSummary = "No changes detected";
            return diff;
        }

        try {
            // Parse both method versions with Spoon
            CtMethod<?> beforeMethod = parseMethodWithSpoon(beforeBody);
            CtMethod<?> afterMethod = parseMethodWithSpoon(afterBody);

            if (beforeMethod != null && afterMethod != null) {
                // Analyze different types of changes
                analyzeSignatureChanges(beforeMethod, afterMethod, diff);
                analyzeAssertionChanges(beforeMethod, afterMethod, diff);
                analyzeImportChanges(beforeBody, afterBody, diff);
                analyzeLogicChanges(beforeMethod, afterMethod, diff);

                // Determine overall change type and refactoring
                classifyRefactoringType(before, after, diff);

                // Generate summary
                generateDiffSummary(diff);
            } else {
                // Fallback to text-based diff if AST parsing fails
                fallbackTextDiff(beforeBody, afterBody, diff);
            }

        } catch (Exception e) {
            // Fallback to text-based diff on any AST error
            fallbackTextDiff(beforeBody, afterBody, diff);
        }

        return diff;
    }

    /**
     * Parse method body using Spoon with better error handling
     */
    private CtMethod<?> parseMethodWithSpoon(String methodBody) {
        if (methodBody == null || methodBody.trim().isEmpty()) {
            return null;
        }

        try {
            // Create a temporary class wrapper for the method
            String classWrapper = "public class TempClass {\n" + methodBody + "\n}";

            Launcher launcher = new Launcher();
            launcher.getEnvironment().setNoClasspath(true);
            launcher.getEnvironment().setIgnoreDuplicateDeclarations(true);
            launcher.getEnvironment().setComplianceLevel(11); // Use Java 11

            // Add the wrapped class as a virtual file
            launcher.addInputResource(new spoon.support.compiler.VirtualFile(classWrapper));

            CtModel model = launcher.buildModel();

            // Extract the method
            List<CtMethod<?>> methods = model.getElements(new TypeFilter<>(CtMethod.class));
            return methods.isEmpty() ? null : methods.get(0);

        } catch (Exception e) {
            // Don't log every parsing failure - they're common with partial code
            return null;
        }
    }

    /**
     * Analyze method signature changes
     */
    private void analyzeSignatureChanges(CtMethod<?> before, CtMethod<?> after, MethodDiff diff) {
        diff.methodNameBefore = before.getSimpleName();
        diff.methodNameAfter = after.getSimpleName();

        // Check method name change
        if (!before.getSimpleName().equals(after.getSimpleName())) {
            diff.hasSignatureChange = true;
            diff.logicChanges.add("Method renamed from " + before.getSimpleName() + " to " + after.getSimpleName());
        }

        // Check parameter changes
        List<CtParameter<?>> beforeParams = before.getParameters();
        List<CtParameter<?>> afterParams = after.getParameters();

        if (beforeParams.size() != afterParams.size()) {
            diff.hasSignatureChange = true;
            diff.logicChanges.add("Parameter count changed from " + beforeParams.size() + " to " + afterParams.size());
        } else {
            // Check parameter types and names
            for (int i = 0; i < beforeParams.size(); i++) {
                CtParameter<?> beforeParam = beforeParams.get(i);
                CtParameter<?> afterParam = afterParams.get(i);

                if (!beforeParam.getType().toString().equals(afterParam.getType().toString())) {
                    diff.hasSignatureChange = true;
                    diff.logicChanges.add("Parameter " + i + " type changed from " +
                            beforeParam.getType() + " to " + afterParam.getType());
                }

                if (!beforeParam.getSimpleName().equals(afterParam.getSimpleName())) {
                    diff.hasSignatureChange = true;
                    diff.logicChanges.add("Parameter " + i + " renamed from " +
                            beforeParam.getSimpleName() + " to " + afterParam.getSimpleName());
                }
            }
        }
    }

    /**
     * Analyze assertion changes with better detection
     */
    private void analyzeAssertionChanges(CtMethod<?> before, CtMethod<?> after, MethodDiff diff) {
        // Extract assertion statements
        List<String> beforeAssertions = extractAssertions(before);
        List<String> afterAssertions = extractAssertions(after);

        // Compare assertion counts
        if (beforeAssertions.size() != afterAssertions.size()) {
            diff.assertionChanges.add("Assertion count changed from " + beforeAssertions.size() +
                    " to " + afterAssertions.size());
        }

        // Find added/removed assertions
        Set<String> beforeSet = new HashSet<>(beforeAssertions);
        Set<String> afterSet = new HashSet<>(afterAssertions);

        Set<String> added = new HashSet<>(afterSet);
        added.removeAll(beforeSet);

        Set<String> removed = new HashSet<>(beforeSet);
        removed.removeAll(afterSet);

        for (String assertion : added) {
            diff.assertionChanges.add("Added: " + cleanAssertionText(assertion));
        }

        for (String assertion : removed) {
            diff.assertionChanges.add("Removed: " + cleanAssertionText(assertion));
        }

        // Detect assertion type changes (e.g., assertEquals -> assertNotNull)
        detectAssertionTypeChanges(beforeAssertions, afterAssertions, diff);
    }

    /**
     * Clean assertion text for better readability
     */
    private String cleanAssertionText(String assertion) {
        return assertion.replaceAll("\\s+", " ").trim();
    }

    /**
     * Detect assertion type changes
     */
    private void detectAssertionTypeChanges(List<String> before, List<String> after, MethodDiff diff) {
        Map<String, Integer> beforeTypes = getAssertionTypeCounts(before);
        Map<String, Integer> afterTypes = getAssertionTypeCounts(after);

        for (String type : beforeTypes.keySet()) {
            int beforeCount = beforeTypes.get(type);
            int afterCount = afterTypes.getOrDefault(type, 0);

            if (beforeCount != afterCount) {
                diff.assertionChanges.add("Assertion type " + type + " count changed from " +
                        beforeCount + " to " + afterCount);
            }
        }

        // Check for new assertion types
        for (String type : afterTypes.keySet()) {
            if (!beforeTypes.containsKey(type)) {
                diff.assertionChanges.add("New assertion type: " + type);
            }
        }
    }

    /**
     * Get assertion type counts
     */
    private Map<String, Integer> getAssertionTypeCounts(List<String> assertions) {
        Map<String, Integer> counts = new HashMap<>();

        for (String assertion : assertions) {
            String type = extractAssertionType(assertion);
            counts.put(type, counts.getOrDefault(type, 0) + 1);
        }

        return counts;
    }

    /**
     * Extract assertion type (assertEquals, assertNotNull, etc.)
     */
    private String extractAssertionType(String assertion) {
        if (assertion.contains("assertEquals")) return "assertEquals";
        if (assertion.contains("assertNotNull")) return "assertNotNull";
        if (assertion.contains("assertNull")) return "assertNull";
        if (assertion.contains("assertTrue")) return "assertTrue";
        if (assertion.contains("assertFalse")) return "assertFalse";
        if (assertion.contains("assertSame")) return "assertSame";
        if (assertion.contains("assertNotSame")) return "assertNotSame";
        return "unknown";
    }

    /**
     * Extract assertion statements from method
     */
    private List<String> extractAssertions(CtMethod<?> method) {
        List<String> assertions = new ArrayList<>();

        if (method.getBody() != null) {
            method.getBody().getStatements().forEach(statement -> {
                String stmt = statement.toString();
                if (stmt.contains("assert") || stmt.contains("Assert.")) {
                    assertions.add(stmt.trim());
                }
            });
        }

        return assertions;
    }

    /**
     * Analyze import changes with better pattern detection
     */
    private void analyzeImportChanges(String beforeBody, String afterBody, MethodDiff diff) {
        List<String> beforeImportUsages = extractImportUsages(beforeBody);
        List<String> afterImportUsages = extractImportUsages(afterBody);

        if (!beforeImportUsages.equals(afterImportUsages)) {
            // Find specific changes
            Set<String> beforeSet = new HashSet<>(beforeImportUsages);
            Set<String> afterSet = new HashSet<>(afterImportUsages);

            Set<String> added = new HashSet<>(afterSet);
            added.removeAll(beforeSet);

            Set<String> removed = new HashSet<>(beforeSet);
            removed.removeAll(afterSet);

            for (String usage : added) {
                diff.importChanges.add("New usage: " + usage);
            }

            for (String usage : removed) {
                diff.importChanges.add("Removed usage: " + usage);
            }
        }
    }

    /**
     * Extract import usage patterns from method body
     */
    private List<String> extractImportUsages(String methodBody) {
        List<String> usages = new ArrayList<>();

        // Look for package patterns in the method body
        String[] lines = methodBody.split("\n");
        for (String line : lines) {
            // Match patterns like org.junit.Assert, org.apache.http.HttpStatus, etc.
            if (line.matches(".*\\b(org|com|java|javax)\\.[a-zA-Z][a-zA-Z0-9._]*[A-Z][a-zA-Z0-9_]*\\b.*")) {
                String[] parts = line.split("\\s+");
                for (String part : parts) {
                    if (part.matches("(org|com|java|javax)\\.[a-zA-Z][a-zA-Z0-9._]*[A-Z][a-zA-Z0-9_]*")) {
                        usages.add(part.replaceAll("[^a-zA-Z0-9.]", ""));
                    }
                }
            }
        }

        return usages.stream().distinct().sorted().collect(Collectors.toList());
    }

    /**
     * Analyze logic changes
     */
    private void analyzeLogicChanges(CtMethod<?> before, CtMethod<?> after, MethodDiff diff) {
        // Compare method complexity, structure, etc.
        int beforeStatements = before.getBody() != null ? before.getBody().getStatements().size() : 0;
        int afterStatements = after.getBody() != null ? after.getBody().getStatements().size() : 0;

        if (beforeStatements != afterStatements) {
            diff.logicChanges.add("Statement count changed from " + beforeStatements + " to " + afterStatements);
        }

        // Analyze control flow changes
        analyzeControlFlowChanges(before, after, diff);
    }

    /**
     * Analyze control flow changes (if/else, loops, try/catch)
     */
    private void analyzeControlFlowChanges(CtMethod<?> before, CtMethod<?> after, MethodDiff diff) {
        // This could be enhanced with more sophisticated control flow analysis
        String beforeStr = before.toString();
        String afterStr = after.toString();

        String[] controlKeywords = {"if", "else", "for", "while", "try", "catch", "switch"};

        for (String keyword : controlKeywords) {
            int beforeCount = countOccurrences(beforeStr, keyword + " ");
            int afterCount = countOccurrences(afterStr, keyword + " ");

            if (beforeCount != afterCount) {
                diff.logicChanges.add("Control flow '" + keyword + "' usage changed from " +
                        beforeCount + " to " + afterCount);
            }
        }
    }

    /**
     * Count occurrences of a substring
     */
    private int countOccurrences(String text, String substring) {
        int count = 0;
        int index = 0;
        while ((index = text.indexOf(substring, index)) != -1) {
            count++;
            index += substring.length();
        }
        return count;
    }

    /**
     * Classify the type of refactoring with more categories
     */
    private void classifyRefactoringType(Map<String, Object> before, Map<String, Object> after, MethodDiff diff) {
        if (diff.hasSignatureChange) {
            if (!diff.methodNameBefore.equals(diff.methodNameAfter)) {
                diff.refactoringType = "METHOD_RENAME";
            } else {
                diff.refactoringType = "SIGNATURE_CHANGE";
            }
        } else if (!diff.assertionChanges.isEmpty()) {
            // More specific assertion refactoring types
            if (diff.assertionChanges.stream().anyMatch(change -> change.contains("type") && change.contains("changed"))) {
                diff.refactoringType = "ASSERTION_TYPE_CHANGE";
            } else {
                diff.refactoringType = "ASSERTION_REFACTORING";
            }
        } else if (!diff.importChanges.isEmpty()) {
            // Check for specific import patterns
            if (diff.importChanges.stream().anyMatch(change -> change.toLowerCase().contains("http"))) {
                diff.refactoringType = "HTTP_LIBRARY_CHANGE";
            } else {
                diff.refactoringType = "IMPORT_REFACTORING";
            }
        } else if (!diff.logicChanges.isEmpty()) {
            if (diff.logicChanges.stream().anyMatch(change -> change.contains("control flow"))) {
                diff.refactoringType = "CONTROL_FLOW_REFACTORING";
            } else {
                diff.refactoringType = "LOGIC_REFACTORING";
            }
        } else {
            diff.refactoringType = "MINOR_CHANGE";
        }

        diff.changeType = diff.refactoringType.toLowerCase().replace("_", " ");
    }

    /**
     * Generate diff summary that is understandable
     */
    private void generateDiffSummary(MethodDiff diff) {
        List<String> summaryParts = new ArrayList<>();

        if (diff.hasSignatureChange) {
            summaryParts.add("signature modified");
        }
        if (!diff.assertionChanges.isEmpty()) {
            summaryParts.add(diff.assertionChanges.size() + " assertion changes");
        }
        if (!diff.importChanges.isEmpty()) {
            summaryParts.add("import usage changes");
        }
        if (!diff.logicChanges.isEmpty()) {
            summaryParts.add(diff.logicChanges.size() + " logic changes");
        }

        diff.diffSummary = summaryParts.isEmpty() ? "Minor formatting changes" : String.join(", ", summaryParts);
    }

    /**
     * Fallback text-based diff with better analysis
     */
    private void fallbackTextDiff(String before, String after, MethodDiff diff) {
        if (before == null || after == null) {
            diff.changeType = "null_change";
            diff.refactoringType = "NULL_CHANGE";
            diff.diffSummary = "Null method body detected";
            return;
        }

        if (!before.equals(after)) {
            diff.changeType = "text_change";

            // Simple line-based comparison
            String[] beforeLines = before.split("\n");
            String[] afterLines = after.split("\n");

            if (beforeLines.length != afterLines.length) {
                diff.logicChanges.add("Line count changed from " + beforeLines.length + " to " + afterLines.length);
            }

            // Check for assertion changes in text
            long beforeAssertCount = Arrays.stream(beforeLines).filter(line -> line.contains("assert")).count();
            long afterAssertCount = Arrays.stream(afterLines).filter(line -> line.contains("assert")).count();

            if (beforeAssertCount != afterAssertCount) {
                diff.assertionChanges.add("Assertion line count changed from " + beforeAssertCount + " to " + afterAssertCount);
                diff.refactoringType = "ASSERTION_REFACTORING";
            } else {
                diff.refactoringType = "TEXT_CHANGE";
            }

            diff.diffSummary = "Text-based diff detected changes";
        } else {
            diff.changeType = "no_change";
            diff.refactoringType = "NO_CHANGE";
            diff.diffSummary = "No changes detected";
        }
    }

    /**
     * Check if changes are significant enough to create a chunk
     */
    private boolean hasSignificantChanges(MethodDiff diff) {
        return !diff.refactoringType.equals("NO_CHANGE") &&
                !diff.refactoringType.equals("MINOR_CHANGE") &&
                (diff.hasSignatureChange ||
                        !diff.assertionChanges.isEmpty() ||
                        !diff.importChanges.isEmpty() ||
                        !diff.logicChanges.isEmpty());
    }

    /**
     * Create a method chunk for RAG with rich metadata
     */
    private MethodChunk createMethodChunk(String filePath, String methodName,
                                          Map<String, Object> before, Map<String, Object> after, MethodDiff diff) {

        MethodChunk chunk = new MethodChunk();

        chunk.chunkId = methodName + "_" + before.get("commit") + "_to_" + after.get("commit");
        chunk.filePath = filePath;
        chunk.methodName = methodName;
        chunk.beforeCommit = (String) before.get("commit");
        chunk.afterCommit = (String) after.get("commit");

        // Copy before state
        chunk.beforeState.putAll(before);

        // Copy after state
        chunk.afterState.putAll(after);

        // Add changes
        chunk.changes = diff;

        // Add rich metadata for RAG retrieval
        chunk.metadata.put("refactoring_type", diff.refactoringType);
        chunk.metadata.put("change_significance", hasSignificantChanges(diff) ? "significant" : "minor");
        chunk.metadata.put("has_assertion_changes", !diff.assertionChanges.isEmpty());
        chunk.metadata.put("has_signature_changes", diff.hasSignatureChange);
        chunk.metadata.put("has_import_changes", !diff.importChanges.isEmpty());
        chunk.metadata.put("has_logic_changes", !diff.logicChanges.isEmpty());
        chunk.metadata.put("change_count", diff.assertionChanges.size() + diff.importChanges.size() + diff.logicChanges.size());

        // Add assertion-specific metadata
        if (!diff.assertionChanges.isEmpty()) {
            chunk.metadata.put("assertion_change_types",
                    diff.assertionChanges.stream()
                            .map(change -> change.split(":")[0])
                            .distinct()
                            .collect(Collectors.toList())
            );
        }

        return chunk;
    }

    /**
     * Read existing JSON data
     */
    private Map<String, Map<String, List<Map<String, Object>>>> readExistingData(String filePath) throws IOException {
        try (FileReader reader = new FileReader(filePath)) {
            Type type = new TypeToken<Map<String, Map<String, List<Map<String, Object>>>>>() {}.getType();
            Map<String, Map<String, List<Map<String, Object>>>> data = gson.fromJson(reader, type);
            return data != null ? data : new HashMap<>();
        }
    }

    /**
     * Save enhanced data with diff information
     */
    private void saveEnhancedData(String filePath, Map<String, Map<String, List<Map<String, Object>>>> data) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(data, writer);
            System.out.println("Enhanced data saved to: " + filePath);
        }
    }

    /**
     * Save chunks for RAG
     */
    private void saveChunks(String filePath, List<MethodChunk> chunks) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(chunks, writer);
            System.out.println("Chunks saved to: " + filePath);
        }
    }
}
