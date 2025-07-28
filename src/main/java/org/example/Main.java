package org.example;

public class Main {
    public static void main(String[] args) {
        try {
            // Parse command line arguments
            String repoPath = "/Users/jesusvaladez/Desktop/RefactoringMiner 2/src/main/resources/commons-validator/";
            String outputFilePath = "test_method_evolution_for_NEW_commons-validator_automated_with_jprofiler.json";

            // Check for command line arguments
            if (args.length > 0) {
                for (int i = 0; i < args.length; i++) {
                    if (args[i].equals("--repo") && i + 1 < args.length) {
                        repoPath = args[i + 1];
                    } else if (args[i].equals("--output") && i + 1 < args.length) {
                        outputFilePath = args[i + 1];
                    }
                }
            }

            System.out.println("=== AUTOMATED TEST METHOD ANALYSIS PIPELINE WITH JPROFILER ===");
            System.out.println("Repository path: " + repoPath);
            System.out.println("Output file: " + outputFilePath);

            // Check JProfiler setup
            String jprofilerHome = System.getenv("JPROFILER_HOME");
            if (jprofilerHome == null) {
                System.err.println("WARNING: JPROFILER_HOME not set - assertion tracing will be disabled");
                System.err.println("To enable JProfiler features, set: export JPROFILER_HOME=/path/to/jprofiler");
            } else {
                System.out.println("JProfiler home: " + jprofilerHome);
            }

            // Store original branch
            TraverseCommit traverseCommit = new TraverseCommit(repoPath);
            String originalBranch = traverseCommit.getCurrentBranch();
            System.out.println("Current branch: " + originalBranch);

            // Set up services
            OutputService outputService = new OutputServiceImpl();

            // Create and run analyzer
            JavaFileAnalyzer analyzer = new JavaFileAnalyzer(outputService, traverseCommit, outputFilePath);

            System.out.println("\n=== PHASE 1: Collecting Test Method Evolution Data ===");
            analyzer.analyzeTestEvolution(repoPath, originalBranch);

            System.out.println("\n=== PIPELINE COMPLETE ===");
            System.out.println("Generated files:");
            System.out.println("1. " + outputFilePath + " - Raw test method evolution with assertion traces");
            System.out.println("2. " + outputFilePath.replace(".json", "_with_diffs.json") + " - Enhanced with diff analysis");
            System.out.println("3. " + outputFilePath.replace(".json", "_chunks.json") + " - RAG-ready chunks with runtime behavior");

            // Optional: Test JProfiler integration
            if (jprofilerHome != null) {
                testJProfilerIntegration(traverseCommit);
            }

        } catch (Exception e) {
            System.err.println("Error in analysis pipeline: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Test JProfiler integration with a simple example
     */
    private static void testJProfilerIntegration(TraverseCommit traverseCommit) {
        try {
            System.out.println("\n=== TESTING JPROFILER INTEGRATION ===");

            // Test with a mock scenario
            AssertionTraceResults results = traverseCommit.runTestsWithProfiling("test-commit", "TestFile.java");

            System.out.println("JProfiler integration test results:");
            System.out.println("- Total assertions traced: " + results.getTotalAssertions());
            System.out.println("- Passed: " + results.getPassedAssertions());
            System.out.println("- Failed: " + results.getFailedAssertions());
            System.out.println("- Total execution time: " + results.getTotalExecutionTime() + "ns");
            System.out.println("- Assertion types: " + results.getAssertionTypeBreakdown());

            if (results.getTotalAssertions() > 0) {
                System.out.println("JProfiler integration working!");
            } else {
                System.out.println("JProfiler integration needs actual implementation");
            }

        } catch (Exception e) {
            System.err.println("JProfiler integration test failed: " + e.getMessage());
        }
    }
}
