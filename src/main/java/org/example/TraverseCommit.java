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
import java.util.List;

/*  Things to do for this file:
 *  1. Have to make it utility (static methods, etc)
 *  2. Need to implement refactoring miner and git checkout
 *  3. Make method to get repo paths?
 *
 */

public final class TraverseCommit {
    private final Git git;
    private final String baseRepoPath;


    public TraverseCommit(String repoPath) throws Exception {
        System.out.println("[DEBUG] Opening Git repository at: " + repoPath);
        this.baseRepoPath = repoPath; // Store base path for later use
        FileRepositoryBuilder repositoryBuilder = new FileRepositoryBuilder();
        Repository repository = repositoryBuilder.setGitDir(new File(repoPath, ".git"))
                .readEnvironment()
                .findGitDir()
                .build();
        this.git = new Git(repository);
        System.out.println("[DEBUG] Repository successfully opened.");
    }


    // Uses JGIT to get commit IDs for test files
    public List<String> getCommitIdsForTestFiles(List<String> testFiles) throws GitAPIException {
        List<String> commitIds = new ArrayList<>();
        System.out.println("[DEBUG] Fetching commit history for test files...");

        // Loop through each test file and get its commit history
        for (String fullFilePath : testFiles) {
            // Shorten the file path
            String relativeFilePath = getRelativeFilePath(fullFilePath);
            System.out.println("[DEBUG] Checking comit history for: " + relativeFilePath);
            Iterable<RevCommit> commits = git.log().addPath(relativeFilePath).call();
            for (RevCommit commit : commits) {
                commitIds.add(commit.getId().getName());
                System.out.println("[DEBUG] Found commit ID: " + commit.getId().getName() + " for file: " + relativeFilePath);
            }
        }
        return commitIds;
    }

    public String getRelativeFilePath(String fullFilePath) {
        // Remove the base repository path from the full file path
        return fullFilePath.replace(baseRepoPath, "");
    }


    public String refactoringMinerOutput(List<String> testFiles) throws Exception {
        // Use getCommitIdsForTestFiles method to store it in a variable
        List<String> commitIDs = getCommitIdsForTestFiles(testFiles);

        // Use string builder to store output of each commit ID
        StringBuilder output = new StringBuilder();

        // Start implementation of refactoring miner
        GitService gitService = new GitServiceImpl();
        GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();
        Repository repo = gitService.cloneIfNotExists("temp/ambari", "https://github.com/apache/ambari.git");
        // Iterate through list of commit IDs and use refactoring miner on each
        for (String commitId : commitIDs) {
            output.append("Refactoring Miner output for commit ").append(commitIDs).append("is:");
            miner.detectAtCommit(repo, commitId, new RefactoringHandler() {
                @Override
                public void handle(String commitId, List<Refactoring> refactorings) {
                    for (Refactoring ref: refactorings) {
                        output.append(ref.toString()).append("\n");
                    }
                    System.out.println(output.toString());
                }
            });
        }
        repo.close();
        return output.toString();
    }


    public static void main(String[] args) {
        String repoPath = "/Users/jesusvaladez/Desktop/RefactoringMiner 2/src/main/resources/ambari/";

        try {
            List<String> testFiles = JavaFileAnalyzer.analyzeTests();
            TraverseCommit traverseCommit = new TraverseCommit(repoPath);

            // Get refactoringMiner output
            String refactorings = traverseCommit.refactoringMinerOutput(testFiles);
            System.out.println("Final Refactoring Output: \n" + refactorings);

            List<String> commitIds = traverseCommit.getCommitIdsForTestFiles(testFiles);
            System.out.println("Commit IDs related to test files:");
            commitIds.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
