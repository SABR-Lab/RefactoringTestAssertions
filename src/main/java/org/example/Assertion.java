package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jgit.lib.Repository;
import org.refactoringminer.api.GitHistoryRefactoringMiner;
import org.refactoringminer.api.GitService;
import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringHandler;
import org.refactoringminer.rm1.GitHistoryRefactoringMinerImpl;
import org.refactoringminer.util.GitServiceImpl;
import spoon.Launcher;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.factory.Factory;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.support.compiler.VirtualFile;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Assertion extends AbstractProcessor<CtMethod<?>> {

    int totalNumOfTests = 0;
    int testsWithAssertion = 0;
    int testsWithoutAssertion = 0;
    int testsWithNullBody = 0;

    public Assertion() {
    }

    private Map<String, List<String>> testMethodsByCommit = new LinkedHashMap<>(); // Stores test code per commit
    @Override
    public void process(CtMethod<?> method) {

        // Check for tests in general -> Has annotation of @Test or Test
        boolean isTestCase = method.getAnnotations().stream()
                .anyMatch(annot -> annot.getAnnotationType().getSimpleName().equals("Test")
                        || method.getSimpleName().startsWith("test") ||
                        method.getSimpleName().endsWith("Test"));

        // If there is a test case then do the following
        if (isTestCase) {
            // Tally amount of tests
            totalNumOfTests++;

            // Important values
            String testName = method.getSimpleName();
            String filePath;
            String assertionState = "FALSE";
            int numberOfAssertions = 0;
            String kindOfAssertion = "None";
            String methodSignature = "None";
            String annotations = "None";
            String bodyCode = "N/A";

            // Get annotations
            annotations = method.getAnnotations().stream()
                    .map(annot -> annot.getAnnotationType().getSimpleName())
                    .reduce((a, b) -> a + "\n" + b)
                    .orElse("None");

            // Gets file path of Test
            if (method.getPosition() != null && method.getPosition().getFile() != null) {
                filePath = method.getPosition().getFile().getAbsolutePath();
                int startOfPath = filePath.indexOf("ambari");
                if (startOfPath != -1) {
                    filePath = filePath.substring(startOfPath).replace("\\", "/");
                }

                // CHANGED THIS
                List<String> commitIDs = onlyCommitHashes(filePath);
                String gitLog = getGitLogForFile(filePath);
                System.out.println("Extracted Commit IDs: " + commitIDs);
                System.out.println("Extracted Git Changes for " + testName + ":\n" + gitLog);

                // Store changes by commit for JSON output
                testMethodsByCommit.put(testName, Collections.singletonList(gitLog));
                JSONOutput();
            }

            // Logic of body
            if (method.getBody() != null) {
                // Gets body code
                bodyCode = method.getBody().toString();

                // Gets the number of assertions and the kind
                List<CtInvocation> assertionInvocations = method.getBody()
                        .getElements(new TypeFilter<>(CtInvocation.class)).stream()
                        .filter(invocation -> invocation.getExecutable()
                                .getSimpleName().startsWith("assert"))
                        .collect(Collectors.toList()); // toList(); before
                numberOfAssertions = assertionInvocations.size();
                kindOfAssertion = assertionInvocations.stream().map(invocation -> invocation.getExecutable().getSimpleName())
                        .distinct()
                        .reduce((a, b) -> a + "\n" + b)
                        .orElse("None");

                // Check if the test contains assertions within
                boolean hasAssertions = method.getBody()
                        .getElements(new TypeFilter<>(CtInvocation.class)).stream()
                        .anyMatch(invocation -> invocation.getExecutable()
                                .getSimpleName().startsWith("assert"));

                if (hasAssertions) {
                    testsWithAssertion++;
                    assertionState = "TRUE";
                    numberOfAssertions++;
                    //System.out.println(testName + ": Is a test case and contains assertion");
                } else {
                    testsWithoutAssertion++;
                    assertionState = "FALSE";
                    // System.out.println(testName + ": Is a test case, but does NOT contain assertion");
                }
            } else {
                testsWithNullBody++;
            }
        }
    }

    @Override
    public void processingDone() {
        // Purpose: Print test totals
        System.out.println("Total Number of Tests: " + totalNumOfTests);
        System.out.println("Tests with Assertion: " + testsWithAssertion);
        System.out.println("Tests without Assertion: " + testsWithoutAssertion);
        System.out.println("Tests with Null Body: " + testsWithNullBody);
    }

    private void JSONOutput() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("git_changes.json"))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            writer.write(gson.toJson(testMethodsByCommit));
            System.out.println("JSON file saved as git_changes.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Retrieves commit IDs of files
    private List<String> onlyCommitHashes(String filePath) {
        String gitBaseDir = System.getProperty("user.home") + "/Desktop/SoftwareTestingTool/src/main/resources/ambari";

        // Full path to git executable (adjust for your system)
        // Update this as needed/ Fix
        String gitExecutable = "/opt/homebrew/bin/git";

        try {
            int startOfPath = filePath.indexOf("ambari");
            if (startOfPath == -1) {
                System.err.println("Error: File path does not contain 'ambari'");
            }

            String relativePath = filePath.substring(startOfPath + "ambari".length());
            String adjustedPath = relativePath.replace("\\", "/");
            if (adjustedPath.startsWith("/")) {
                adjustedPath = adjustedPath.substring(1); // Remove leading slash
            }

            // Executes the command
            // Fix this
            ProcessBuilder processBuilder = new ProcessBuilder(gitExecutable, "log", "--pretty=format:%H", "--follow", "--", adjustedPath);
            processBuilder.directory(new File(gitBaseDir));
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                List<String> commitIDs = reader.lines().collect(Collectors.toList());
                System.out.println("The commit IDS here: " + commitIDs);
                if (commitIDs.isEmpty()) {
                    System.err.println("Method onlyCommitHashes returned no results for: " + adjustedPath);
                }
                return commitIDs;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private void gitCheckoutUsage(String filePath) {
        List<String> commitIDs = onlyCommitHashes(filePath);

        if (commitIDs.isEmpty()) {
            System.out.println("No commits found for: " + filePath);
            return;
        }
        String gitBaseDir = System.getProperty("user.home") + "/Desktop/SoftwareTestingTool/src/main/resources/ambari";

        for (String commit : commitIDs) {
            try {
                ProcessBuilder processBuilder = new ProcessBuilder("git", "checkout", commit);
                processBuilder.directory(new File(gitBaseDir));
                processBuilder.redirectErrorStream(true);
                Process process = processBuilder.start();

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    reader.lines().forEach(System.out::println);
                }

                int exitCode = process.waitFor();
                if (exitCode != 0) {
                    System.err.println("Method is not working at the commit: " + commit);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    // This is refactoring miner output
    private String getGitLogForFile(String filePath) {
        String gitBaseDir = System.getProperty("user.home") + "/Desktop/SoftwareTestingTool/src/main/resources/ambari";

        StringBuilder resultOfMethod = new StringBuilder();
        try {
            int startOfPath = filePath.indexOf("ambari");
            if (startOfPath == -1) {
                System.err.println("Error: File path does not contain 'ambari'");
                return "Error: File path does not contain 'ambari'";
            }

            String relativePath = filePath.substring(startOfPath + "ambari".length());
            String adjustedPath = relativePath.replace("\\", "/");
            if (adjustedPath.startsWith("/")) {
                adjustedPath = adjustedPath.substring(1);
            }

            // Executes the command
            // Fix this
            ProcessBuilder processBuilder = new ProcessBuilder("git", "log", "--pretty=format:%H", "--follow", "--", adjustedPath);
            processBuilder.directory(new File(gitBaseDir));
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();


            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                // Convert to list of commit IDs
                List<String> commitIDs = reader.lines().collect(Collectors.toList());
                if (commitIDs.isEmpty()) {
                    System.out.println("Git log returned no results for: " + adjustedPath);
                    return "No commit IDs found";
                }
                // Start implementation of refactoring miner API
                GitService gitService = new GitServiceImpl();
                GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();
                Repository repo = gitService.cloneIfNotExists(
                        "temp/ambari",
                        "https://github.com/apache/ambari.git");

                int versionCounter = 1;
                // Iterates through list (commitIDs)
                for (String commitId : commitIDs) {
                    resultOfMethod.append("Version ").append(versionCounter).append(" refactoring of ").append(commitId).append(" are:\n");
                    miner.detectAtCommit(repo, commitId, new RefactoringHandler() {
                        @Override
                        public void handle(String commitId, List<Refactoring> refactorings) {
                            for (Refactoring ref : refactorings) {
                                resultOfMethod.append(ref.toString()).append("\n");
                            }
                        }
                    });
                    versionCounter++;
                }
                repo.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
            return "Can't get commit ID";
        } catch (Exception e) {
            e.printStackTrace();
            return "API not working";
        }
        return resultOfMethod.toString();
    }


    /* Will implement later once done with git show
    private Map<String, String> getGitDiffForCommits(String filepath, List<String> commitIDs) {
        Map<String, String> commitDiffs = new LinkedHashMap<>();

        String gitBaseDir = System.getProperty("user.home") + "/Desktop/SoftwareTestingTool/src/main/resources/ambari";
        try {
            for (int i = 0; i < commitIDs.size() - 1; i++) {
                String firstCommit = commitIDs.get(i);
                String secondCommit = commitIDs.get(i + 1);

                ProcessBuilder processBuilder =  new ProcessBuilder("git", "diff", firstCommit, secondCommit, "--", filepath);
                processBuilder.directory(new File(gitBaseDir));
                processBuilder.redirectErrorStream(true);
                Process process = processBuilder.start();

                StringBuilder differenceOutputs = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    reader.lines().forEach(line -> differenceOutputs.append(line).append("\n"));
                }
                if (!differenceOutputs.toString().trim().isEmpty()) {
                    commitDiffs.put(firstCommit + "->" + secondCommit, differenceOutputs.toString());
                    // Debugging Output
                    System.out.println("\n Git Diff between " + firstCommit + " and " + secondCommit + " for " + filepath + ":\n" + commitDiffs);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return commitDiffs;
    }

     */
}

/*
private String getGitLogForFile(String filePath) {
        String gitBaseDir = System.getProperty("user.home") + "/Desktop/SoftwareTestingTool/src/main/resources/ambari";

        // Full path to git executable (adjust for your system)
        // Update this as needed/ Fix
        String gitExecutable = "/opt/homebrew/bin/git";

        StringBuilder resultOfMethod = new StringBuilder();

        try {
            int startOfPath = filePath.indexOf("ambari");
            if (startOfPath == -1) {
                System.err.println("Error: File path does not contain 'ambari'");
                return "Error: File path does not contain 'ambari'";
            }

            String relativePath = filePath.substring(startOfPath + "ambari".length());
            String adjustedPath = relativePath.replace("\\", "/");
            if (adjustedPath.startsWith("/")) {
                adjustedPath = adjustedPath.substring(1); // Remove leading slash
            }

            // Executes the command
            // Fix this
            ProcessBuilder processBuilder = new ProcessBuilder(gitExecutable, "log", "--pretty=format:%H", "--follow", "--", adjustedPath);
            processBuilder.directory(new File(gitBaseDir));
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();


            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                // Convert to list of commit IDs
                List<String> commitIDs = reader.lines().toList();
                if (commitIDs.isEmpty()) {
                    System.out.println("Git log returned no results for: " + adjustedPath);
                    return "No commit IDs found";
                }


                // Start implementation of refactoring miner API
                GitService gitService = new GitServiceImpl();
                GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();
                Repository repo = gitService.cloneIfNotExists(
                        "temp/ambari",
                        "https://github.com/apache/ambari.git");

                // Iterates through list (commitIDs)
                for (String commitId : commitIDs) {
                    resultOfMethod.append("Refactorings at commit are ").append(commitId).append(":\n");
                    miner.detectAtCommit(repo, commitId, new RefactoringHandler() {
                        @Override
                        public void handle(String commitId, List<Refactoring> refactorings) {
                            for (Refactoring ref : refactorings) {
                                resultOfMethod.append(ref.toString()).append("\n");
                            }
                        }
                    });
                }
                repo.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Can't get commit ID";
        } catch (Exception e) {
            e.printStackTrace();
            return "API not working";
        }
        return resultOfMethod.toString();
    }

}




private String getGitLogForFile(String filePath) {
        String gitBaseDir = System.getProperty("user.home") + "/Desktop/SoftwareTestingTool/src/main/resources/ambari";

        // Full path to git executable (adjust for your system)
        // Update this as needed/ Fix
        String gitExecutable = "/opt/homebrew/bin/git";

        try {
            int startOfPath = filePath.indexOf("ambari");
            if (startOfPath == -1) {
                System.err.println("Error: File path does not contain 'ambari'");
                return "Error: File path does not contain 'ambari'";
            }

            String relativePath = filePath.substring(startOfPath + "ambari".length());
            String adjustedPath = relativePath.replace("\\", "/");
            if (adjustedPath.startsWith("/")) {
                adjustedPath = adjustedPath.substring(1); // Remove leading slash
            }

            // Executes the command
            // Fix this
            ProcessBuilder processBuilder = new ProcessBuilder(gitExecutable, "log", "--pretty=format:%H", "--follow", "--", adjustedPath);
            processBuilder.directory(new File(gitBaseDir));
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String commitIDs = reader.lines().collect(Collectors.joining("\n"));
                if (commitIDs.isEmpty()) {
                    System.err.println("Git log returned no results for: " + adjustedPath);
                    return "No commit IDs found";
                }
                return commitIDs;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error retrieving commit ID";
        }
    }
}




This version is the most recent one
    private String getGitLogForFile(String filePath) {
        String gitBaseDir = System.getProperty("user.home") + "/Desktop/SoftwareTestingTool/src/main/resources/ambari";

        // Full path to git executable (adjust for your system)
        // Update this as needed/ Fix
        String gitExecutable = "/opt/homebrew/bin/git";

        StringBuilder resultOfMethod = new StringBuilder();
        try {
            int startOfPath = filePath.indexOf("ambari");
            if (startOfPath == -1) {
                System.err.println("Error: File path does not contain 'ambari'");
                return "Error: File path does not contain 'ambari'";
            }

            String relativePath = filePath.substring(startOfPath + "ambari".length());
            String adjustedPath = relativePath.replace("\\", "/");
            if (adjustedPath.startsWith("/")) {
                adjustedPath = adjustedPath.substring(1); // Remove leading slash
            }

            // Executes the command
            // Fix this
            ProcessBuilder processBuilder = new ProcessBuilder(gitExecutable, "log", "--pretty=format:%H", "--follow", "--", adjustedPath);
            processBuilder.directory(new File(gitBaseDir));
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();


            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                // Convert to list of commit IDs
                List<String> commitIDs = reader.lines().collect(Collectors.toList());
                if (commitIDs.isEmpty()) {
                    System.out.println("Git log returned no results for: " + adjustedPath);
                    return "No commit IDs found";
                }
                // Start implementation of refactoring miner API
                GitService gitService = new GitServiceImpl();
                GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();
                Repository repo = gitService.cloneIfNotExists(
                        "temp/ambari",
                        "https://github.com/apache/ambari.git");

                int versionCounter = 1;
                // Iterates through list (commitIDs)
                for (String commitId : commitIDs) {
                    resultOfMethod.append("Version ").append(versionCounter).append(" refactoring of ").append(commitId).append(" are:\n");
                    miner.detectAtCommit(repo, commitId, new RefactoringHandler() {
                        @Override
                        public void handle(String commitId, List<Refactoring> refactorings) {
                            for (Refactoring ref : refactorings) {
                                resultOfMethod.append(ref.toString()).append("\n");
                            }
                        }
                    });
                    versionCounter++;
                    resultOfMethod.append("\n");
                }
                repo.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Can't get commit ID";
        } catch (Exception e) {
            e.printStackTrace();
            return "API not working";
        }
        return resultOfMethod.toString();
    }
 */


