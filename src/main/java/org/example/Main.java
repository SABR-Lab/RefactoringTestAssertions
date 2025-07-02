package org.example;

public class Main {
    public static void main(String[] args) {
        try {
            // Parse command line arguments
            String repoPath = "/Users/jesusvaladez/Desktop/RefactoringMiner 2/src/main/resources/ambari/";
            String outputFilePath = "test_method_evolution_for_ambari_test.json";

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

            System.out.println("Repository path: " + repoPath);
            System.out.println("Output file: " + outputFilePath);

            // Store original branch
            TraverseCommit traverseCommit = new TraverseCommit(repoPath);
            String originalBranch = traverseCommit.getCurrentBranch();
            System.out.println("Current branch: " + originalBranch);

            // Set up services
            OutputService outputService = new OutputServiceImpl();

            // Create and run analyzer
            JavaFileAnalyzer analyzer = new JavaFileAnalyzer(outputService, traverseCommit, outputFilePath);
            analyzer.analyzeTestEvolution(repoPath, originalBranch);

            System.out.println("Analysis completed successfully. Results saved to: " + outputFilePath);

        } catch (Exception e) {
            System.err.println("Error in analysis process: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
