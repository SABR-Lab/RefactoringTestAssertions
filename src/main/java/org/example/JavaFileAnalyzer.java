package org.example;

import spoon.Launcher;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.filter.TypeFilter;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class JavaFileAnalyzer {
    private final OutputService outputService;
    private final TraverseCommit traverseCommit;
    private final String outputFilePath;

    public JavaFileAnalyzer(OutputService outputService, TraverseCommit traverseCommit, String outputFilePath) {
        this.outputService = outputService;
        this.traverseCommit = traverseCommit;
        this.outputFilePath = outputFilePath;
    }

    public void analyzeTestEvolution(String repositoryPath, String originalBranch) {
        try {
            // Find all Java files
            List<File> javaFiles = Files.walk(Paths.get(repositoryPath))
                    .filter(path -> path.toString().endsWith(".java"))
                    .map(java.nio.file.Path::toFile)
                    .collect(Collectors.toList());

            System.out.println("Found " + javaFiles.size() + " Java files to analyze");

            // First, identify which files contain test methods
            List<String> testFilePaths = new ArrayList<>();

            for (File file : javaFiles) {
                try {
                    if (containsTestMethods(file.getAbsolutePath())) {
                        testFilePaths.add(file.getAbsolutePath());
                        System.out.println("Found test file: " + file.getAbsolutePath());
                    }
                } catch (Exception e) {
                    System.out.println("Error checking for test methods in " + file.getAbsolutePath() + ": " + e.getMessage());
                }
            }

            System.out.println("Found " + testFilePaths.size() + " files containing test methods");

            // Get all commit history for test files BEFORE checking out any commits
            System.out.println("Collecting commit history for all test files...");
            Map<String, List<String>> fileCommitMap = traverseCommit.getCommitHistoryForFiles(testFilePaths, 3);

            System.out.println("Found commits for " + fileCommitMap.size() + " out of " + testFilePaths.size() + " test files");

            // Initialize result map
            Map<String, Map<String, List<Map<String, Object>>>> allResults = new LinkedHashMap<>();

            // Process each test file that has commits
            for (Map.Entry<String, List<String>> entry : fileCommitMap.entrySet()) {
                String filePath = entry.getKey();
                List<String> commits = entry.getValue();

                System.out.println("\n Processing: " + filePath + " with " + commits.size() + " commits");

                Map<String, List<Map<String, Object>>> fileResults = new LinkedHashMap<>();

                // Process each commit
                for (String commit : commits) {
                    // Checkout commit
                    if (!traverseCommit.checkoutCommit(commit)) {
                        continue;
                    }

                    // Check if file exists at this commit
                    if (!new File(filePath).exists()) {
                        System.out.println("File missing at commit " + commit + ": " + filePath);
                        continue;
                    }

                    // Analyze file for test methods at this commit
                    try {
                        analyzeFileAtCommit(filePath, commit, fileResults);
                    } catch (Exception e) {
                        System.out.println("Error analyzing file at commit " + commit + ": " + e.getMessage());
                    }
                }

                // Only add to results if we found test methods
                if (!fileResults.isEmpty()) {
                    allResults.put(filePath, fileResults);
                }
            }

            // Restore original branch
            traverseCommit.restoreOriginalBranch(originalBranch);

            // Save results to file
            outputService.saveAllTestResults(outputFilePath, allResults);

        } catch (Exception e) {
            System.err.println("Error in analyzeTestEvolution: " + e.getMessage());
            e.printStackTrace();

            // Try to restore original branch in case of error
            try {
                traverseCommit.restoreOriginalBranch(originalBranch);
            } catch (Exception ex) {
                System.err.println("Failed to restore original branch: " + ex.getMessage());
            }
        }
    }

    /**
     * Check if a file contains test methods
     */
    private boolean containsTestMethods(String filePath) {
        try {
            Launcher launcher = new Launcher();
            launcher.getEnvironment().setNoClasspath(true);
            launcher.addInputResource(filePath);

            CtModel model = launcher.buildModel();

            return model.getElements(new TypeFilter<>(CtMethod.class)).stream()
                    .anyMatch(method -> Assertion.isTestMethod(method));
        } catch (Exception e) {
            System.out.println("Error checking for test methods: " + e.getMessage());
            return false;
        }
    }

    private void analyzeFileAtCommit(String filePath, String commit, Map<String, List<Map<String, Object>>> fileResults) {
        // Use Spoon to analyze the file
        Launcher launcher = new Launcher();
        launcher.getEnvironment().setNoClasspath(true);
        launcher.addInputResource(filePath);

        CtModel model = launcher.buildModel();

        // Find all test methods
        for (CtMethod<?> method : model.getElements(new TypeFilter<>(CtMethod.class))) {
            if (Assertion.isTestMethod(method)) {
                String methodName = method.getSimpleName();
                String methodBody = method.toString();

                // Get assertion information using the Assertion class
                String annotations = Assertion.getAnnotation(method);
                int assertionAmount = Assertion.getAssertionAmount(method);
                String assertionType = Assertion.getAssertionType(method);
                List<Integer> assertionLines = Assertion.getAssertionLineNumbers(method);
                String methodSignature = Assertion.getMethodSignature(method);

                // Create result map
                Map<String, Object> methodInfo = new HashMap<>();
                methodInfo.put("commit", commit);
                methodInfo.put("body", methodBody);

                // Add assertion information
                methodInfo.put("method_signature", methodSignature);
                methodInfo.put("annotations", annotations);
                methodInfo.put("assertion_amount", assertionAmount);
                methodInfo.put("assertion_type", assertionType);
                methodInfo.put("assertion_lines", assertionLines);
                methodInfo.put("has_assertions", assertionAmount > 0);

                // Add to results
                fileResults.computeIfAbsent(methodName, k -> new ArrayList<>())
                        .add(methodInfo);

                System.out.println("Found test method: " + methodName +
                        " at commit: " + commit +
                        " with " + assertionAmount + " assertions");
            }
        }
    }
}
