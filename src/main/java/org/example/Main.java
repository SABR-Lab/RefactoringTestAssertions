package org.example;

import java.io.FileWriter;

public class Main {
    public static void main(String[] args) {
        try {
            // Parse command line arguments
            String repoPath = "/Users/jesusvaladez/Desktop/RefactoringMiner 2/src/main/resources/ambari/";
            String outputFilePath = "combined_analysis_results.json";
            String singleFilePath = null;

            // Check for command line arguments
            if (args.length > 0) {
                for (int i = 0; i < args.length; i++) {
                    if (args[i].equals("--repo") && i + 1 < args.length) {
                        repoPath = args[i + 1];
                    } else if (args[i].equals("--output") && i + 1 < args.length) {
                        outputFilePath = args[i + 1];
                    } else if (args[i].equals("--file") && i + 1 < args.length) {
                        singleFilePath = args[i + 1];
                    }
                }
            }

            System.out.println("Repository path: " + repoPath);
            System.out.println("Output file: " + outputFilePath);
            if (singleFilePath != null) {
                System.out.println("Analyzing single file: " + singleFilePath);
            }

            // Initialize output file with a JSON array opener
            try (FileWriter writer = new FileWriter(outputFilePath)) {
                writer.write("[\n");
            }

            // Set up dependencies
            OutputService outputService = new OutputServiceImpl();
            TraverseCommit traverseCommit = new TraverseCommit(repoPath);

            // Initialize the analyzer with dependencies
            JavaFileAnalyzer analyzer = new JavaFileAnalyzer(outputService, traverseCommit, outputFilePath);

            // Run the analysis on a single file or the whole repository
            if (singleFilePath != null) {
                analyzer.analyze(singleFilePath);
            } else {
                analyzer.analyze(repoPath);
            }

            // Close the JSON array
            try (FileWriter writer = new FileWriter(outputFilePath, true)) {
                // Remove the trailing comma if it exists
                java.nio.file.Path path = java.nio.file.Paths.get(outputFilePath);
                String content = java.nio.file.Files.readString(path);
                if (content.endsWith(",\n")) {
                    // Rewrite the file without the trailing comma
                    content = content.substring(0, content.length() - 2) + "\n";
                    java.nio.file.Files.writeString(path, content);
                }

                writer.write("]");
            }

            System.out.println("Analysis completed successfully. Results saved to: " + outputFilePath);

        } catch (Exception e) {
            System.err.println("Error in analysis process: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
