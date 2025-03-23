package org.example;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.refactoringminer.api.GitHistoryRefactoringMiner;
import org.refactoringminer.api.GitService;
import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringHandler;
import org.refactoringminer.rm1.GitHistoryRefactoringMinerImpl;
import org.refactoringminer.util.GitServiceImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TraverseCommit {
    private final Git git;
    private final String baseRepoPath;
    private final Map<String, String> fileToRefactoringsMap;

    public TraverseCommit(String repoPath) throws Exception {
        System.out.println("[DEBUG] Opening Git repository at: " + repoPath);
        this.baseRepoPath = repoPath; // Store base path for later use
        FileRepositoryBuilder repositoryBuilder = new FileRepositoryBuilder();
        Repository repository = repositoryBuilder.setGitDir(new File(repoPath, ".git"))
                .readEnvironment()
                .findGitDir()
                .build();
        this.git = new Git(repository);
        this.fileToRefactoringsMap = new ConcurrentHashMap<>();
        System.out.println("[DEBUG] Repository successfully opened.");
    }

    /**
     * Get commit IDs for test files using JGit
     */
    public List<String> getCommitIdsForTestFiles(List<String> testFiles) throws GitAPIException {
        List<String> commitIds = new ArrayList<>();
        System.out.println("[DEBUG] Fetching commit history for test files...");

        for (String fullFilePath : testFiles) {
            String relativeFilePath = getRelativeFilePath(fullFilePath);
            System.out.println("[DEBUG] Checking commit history for: " + relativeFilePath);
            try {
                Iterable<RevCommit> commits = git.log().addPath(relativeFilePath).call();
                for (RevCommit commit : commits) {
                    String commitId = commit.getId().getName();
                    commitIds.add(commitId);
                    System.out.println("[DEBUG] Found commit ID: " + commitId + " for file: " + relativeFilePath);
                }
            } catch (Exception e) {
                System.err.println("[ERROR] Failed to get commits for " + relativeFilePath + ": " + e.getMessage());
            }
        }
        return commitIds;
    }

    /**
     * Get file path relative to the repository root
     */
    public String getRelativeFilePath(String fullFilePath) {
        // Remove the base repository path from the full file path
        String relativePath = fullFilePath.replace(baseRepoPath, "");

        // Normalize path separators for cross-platform compatibility
        relativePath = relativePath.replace('\\', '/');

        // Remove leading slash if present
        if (relativePath.startsWith("/")) {
            relativePath = relativePath.substring(1);
        }

        return relativePath;
    }

    /**
     * Get all refactorings from the commits that touched this file,
     * not just the ones affecting the file itself
     */
    public String getRefactoringsForCommits(List<String> commitIds, String filePath) throws Exception {
        if (commitIds.isEmpty()) {
            System.out.println("[ERROR] No commits found for file: " + filePath);
            return "No commits found for this file";
        }

        StringBuilder refactoringsBuilder = new StringBuilder();
        String relativePath = getRelativeFilePath(filePath);

        System.out.println("\n==============================================================");
        System.out.println("COLLECTING ALL REFACTORINGS FOR: " + filePath);
        System.out.println("Relative path: " + relativePath);
        System.out.println("Found " + commitIds.size() + " commits");
        for (String id : commitIds) {
            System.out.println("  - " + id);
        }

        // Configure RefactoringMiner
        GitService gitService = new GitServiceImpl();
        GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();
        Repository repo = git.getRepository();

        // Only check for the first 3 commits for performance
        int commitLimit = Math.min(commitIds.size(), 3);
        List<String> limitedCommits = commitIds.subList(0, commitLimit);

        System.out.println("Analyzing " + commitLimit + " commits for refactorings");


        // Process each commit to find refactorings
        for (String commitId : limitedCommits) {
            System.out.println("Checking commit: " + commitId);
            final boolean[] foundInThisCommit = {false};

            miner.detectAtCommit(repo, commitId, new RefactoringHandler() {
                @Override
                public void handle(String currentCommitId, List<Refactoring> refactorings) {
                    System.out.println("  Found " + refactorings.size() + " total refactorings in this commit");

                    if (refactorings.isEmpty()) {
                        return;
                    }

                    foundInThisCommit[0] = true;

                    // Include ALL refactorings from this commit (up to a reasonable limit)
                    int refLimit = Math.min(refactorings.size(), 20); // Limit to first 20 refactorings
                    for (int i = 0; i < refLimit; i++) {
                        Refactoring ref = refactorings.get(i);
                        String refName = ref.getName();

                        // Add refactoring details (for all refactorings, not just those matching the file)
                        String refEntry = "Commit: " + currentCommitId.substring(0, 8) + " - " + refName + "\n";
                        refactoringsBuilder.append(refEntry);
                        System.out.println("    ADDED TO RESULTS: " + refEntry.trim());
                    }

                    if (refactorings.size() > refLimit) {
                        refactoringsBuilder.append("... and " + (refactorings.size() - refLimit) +
                                " more refactorings in this commit\n");
                    }
                }
            });

            if (!foundInThisCommit[0]) {
                System.out.println("  No refactorings found in this commit");
            }
        }

        String result = refactoringsBuilder.toString();
        if (result.isEmpty()) {
            System.out.println("NO REFACTORINGS FOUND IN ANY COMMITS FOR THIS FILE");
            System.out.println("==============================================================\n");
            return "No refactorings found";
        } else {
            System.out.println("FOUND REFACTORINGS IN COMMITS ASSOCIATED WITH THIS FILE:");
            System.out.println(result);
            System.out.println("==============================================================\n");
            return result;
        }
    }



    /**
     * Original method for full refactoring analysis (kept as a guideline for the method above)
     */
    public String refactoringMinerOutput(List<String> testFiles) throws Exception {
        StringBuilder output = new StringBuilder();

        // Get commit IDs for all test files
        List<String> commitIDs = getCommitIdsForTestFiles(testFiles);

        if (commitIDs.isEmpty()) {
            System.out.println("[WARNING] No commits found for the provided test files");
            return "No refactorings found";
        }

        // Configure RefactoringMiner
        GitService gitService = new GitServiceImpl();
        GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();
        Repository repo = git.getRepository();

        // Analyze each commit for refactorings
        for (String commitId : commitIDs) {
            output.append("Analyzing refactorings in commit: ").append(commitId).append("\n");

            miner.detectAtCommit(repo, commitId, new RefactoringHandler() {
                @Override
                public void handle(String currentCommitId, List<Refactoring> refactorings) {
                    if (refactorings.isEmpty()) {
                        output.append("No refactorings found in commit ").append(currentCommitId).append("\n");
                        return;
                    }

                    output.append("Found ").append(refactorings.size())
                            .append(" refactorings in commit ").append(currentCommitId).append("\n");

                    // Process each refactoring
                    for (Refactoring ref : refactorings) {
                        output.append(ref.toString()).append("\n");
                    }
                }
            });
        }
        return output.toString();
    }

}
