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
    private final TestMethodAnalyzer diffAnalyzer;

    public JavaFileAnalyzer(OutputService outputService, TraverseCommit traverseCommit, String outputFilePath) {
        this.outputService = outputService;
        this.traverseCommit = traverseCommit;
        this.outputFilePath = outputFilePath;
        this.diffAnalyzer = new TestMethodAnalyzer();
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

            // Save initial results to file
            outputService.saveAllTestResults(outputFilePath, allResults);

            // Run diff analysis and create chunks
            System.out.println("\n=== Starting Automated Diff Analysis ===");
            diffAnalyzer.analyzeAndCreateChunks(outputFilePath);

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

                // NEW: Collect assertion traces using JProfiler
                AssertionTraceResults traceResults = null;
                try {
                    traceResults = traverseCommit.runTestsWithProfiling(commit, filePath);
                    System.out.println("Collected " + traceResults.getTotalAssertions() + " assertion traces for " + methodName);
                } catch (Exception e) {
                    System.err.println("Failed to collect assertion traces for " + methodName + ": " + e.getMessage());
                }

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

                // NEW: Add assertion trace data
                if (traceResults != null) {
                    methodInfo.put("assertion_traces", traceResults.getTraces());
                    methodInfo.put("trace_statistics", createTraceStatistics(traceResults));
                    methodInfo.put("runtime_behavior", createRuntimeBehaviorSummary(traceResults));
                }

                // Add to results
                fileResults.computeIfAbsent(methodName, k -> new ArrayList<>())
                        .add(methodInfo);

                System.out.println("Found test method: " + methodName +
                        " at commit: " + commit +
                        " with " + assertionAmount + " assertions" +
                        (traceResults != null ? " and " + traceResults.getTotalAssertions() + " traces" : ""));
            }
        }
    }

    /**
     * Create trace statistics summary
     */
    private Map<String, Object> createTraceStatistics(AssertionTraceResults traceResults) {
        Map<String, Object> stats = new HashMap<>();
        stats.put("total_traces", traceResults.getTotalAssertions());
        stats.put("passed_traces", traceResults.getPassedAssertions());
        stats.put("failed_traces", traceResults.getFailedAssertions());
        stats.put("total_execution_time_ns", traceResults.getTotalExecutionTime());
        stats.put("assertion_type_breakdown", traceResults.getAssertionTypeBreakdown());
        stats.put("avg_execution_time_ns",
                traceResults.getTotalAssertions() > 0 ?
                        traceResults.getTotalExecutionTime() / traceResults.getTotalAssertions() : 0);
        return stats;
    }

    /**
     * Creates runtime behavior summary
     */
    private Map<String, Object> createRuntimeBehaviorSummary(AssertionTraceResults traceResults) {
        Map<String, Object> behavior = new HashMap<>();

        // Analyze assertion patterns
        Map<String, Integer> typeBreakdown = traceResults.getAssertionTypeBreakdown();
        behavior.put("most_common_assertion",
                typeBreakdown.entrySet().stream()
                        .max(Map.Entry.comparingByValue())
                        .map(Map.Entry::getKey)
                        .orElse("none"));

        // Performance metrics
        List<AssertionTrace> traces = traceResults.getTraces();
        if (!traces.isEmpty()) {
            long minTime = traces.stream().mapToLong(AssertionTrace::getExecutionTimeNs).min().orElse(0);
            long maxTime = traces.stream().mapToLong(AssertionTrace::getExecutionTimeNs).max().orElse(0);

            behavior.put("fastest_assertion_ns", minTime);
            behavior.put("slowest_assertion_ns", maxTime);
            behavior.put("performance_variance", maxTime - minTime);
        }

        // Success rate
        double successRate = traceResults.getTotalAssertions() > 0 ?
                (double) traceResults.getPassedAssertions() / traceResults.getTotalAssertions() * 100 : 0;
        behavior.put("success_rate_percent", successRate);

        return behavior;
    }
}
