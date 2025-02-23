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
import spoon.reflect.visitor.filter.TypeFilter;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Assertion extends AbstractProcessor<CtMethod<?>> {

    int totalNumOfTests = 0;
    int testsWithAssertion = 0;
    int testsWithoutAssertion = 0;
    int testsWithNullBody = 0;

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
                // String testInformation = gitCheckoutUsage(filePath, testName);
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

    // Retrieves commit IDs of files
    private List<String> onlyCommitHashes(String filePath) {
        String gitBaseDir = System.getProperty("user.home") + "/Desktop/RefactoringMiner 2/src/main/resources/ambari";

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
            ProcessBuilder processBuilder = new ProcessBuilder("git", "log", "--pretty=format:%H", "--follow", "--", adjustedPath);
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

    // This is refactoring miner output
    private String getGitLogForFile(String filePath) {
        String gitBaseDir = System.getProperty("user.home") + "/Desktop/RefactoringMiner 2/src/main/resources/ambari";

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

    /*
    private String gitCheckoutUsage(String filePath, String testName) {
        List<String> commitIDs = onlyCommitHashes(filePath);

        if (commitIDs.isEmpty()) {
            System.out.println("No commits found for: " + filePath);
        }
        String gitBaseDir = System.getProperty("user.home") + "/Desktop/RefactoringMiner 2/src/main/resources/ambari";

        for (String commit : commitIDs) {
            try {
                ProcessBuilder processBuilder = new ProcessBuilder("git", "checkout", commit);
                processBuilder.directory(new File(gitBaseDir));
                processBuilder.redirectErrorStream(true);
                Process process = processBuilder.start();
                int exitCode = process.waitFor();
                if (exitCode != 0) {
                    System.err.println("Method is not working at the commit: " + commit);
                }

                // Start spoon up for analysis of the current version of the test file
                Launcher launcher = new Launcher();
                launcher.addInputResource("/Users/jesusvaladez/Desktop/RefactoringMiner 2/src/main/resources/"  + filePath);
                launcher.buildModel();

                Optional<CtMethod<?>> testMethod = launcher.getFactory().Class().getAll().
                        stream().flatMap(clazz -> clazz.getMethods().stream())
                        .filter(method -> method.getSimpleName().equals(testName))
                        .findFirst();
                CtMethod<?> testMethodd = testMethod.get();

                List<CtInvocation> assertions = testMethod.get()
                        .getElements(new TypeFilter<>(CtInvocation.class)).stream()
                        .filter(inv -> inv.getExecutable().getSimpleName().startsWith("assert"))
                        .collect(Collectors.toList());

                String testBodyCode = testMethodd.getBody().toString();

                System.out.println(assertions);
                System.out.println(testBodyCode);

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

     */

    private void JSONOutput() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("git_changes.json"))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            writer.write(gson.toJson(testMethodsByCommit));
            System.out.println("JSON file saved as git_changes.json");
        } catch (IOException e) {
            e.printStackTrace();
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
}
