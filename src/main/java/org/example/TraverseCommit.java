package org.example;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.*;
import java.util.*;

public class TraverseCommit {
    private final Git git;
    private final String baseRepoPath;

    public TraverseCommit(String repoPath) throws Exception {
        System.out.println("[DEBUG] Opening Git repository at: " + repoPath);
        this.baseRepoPath = repoPath;
        FileRepositoryBuilder repositoryBuilder = new FileRepositoryBuilder();
        Repository repository = repositoryBuilder.setGitDir(new File(repoPath, ".git"))
                .readEnvironment()
                .findGitDir()
                .build();
        this.git = new Git(repository);
        System.out.println("[DEBUG] Repository successfully opened.");
    }

    /**
     * Get last N commits for a file using git log --follow.
     * Always run this BEFORE checking out any commits.
     */
    public List<String> getCommitsForFile(String filePath, int n) {
        List<String> commitIds = new ArrayList<>();
        String relativeFilePath = getRelativeFilePath(filePath);

        try {
            System.out.println("[DEBUG] Getting commits for: " + relativeFilePath);

            // Use git log --follow to get commit history
            String cmd = "git log -n " + n + " --pretty=format:%H --follow -- \"" + relativeFilePath + "\"";
            String output = runGitCommand(cmd, baseRepoPath);

            if (output != null && !output.trim().isEmpty()) {
                String[] commits = output.split("\n");
                commitIds.addAll(Arrays.asList(commits));
                System.out.println("[DEBUG] Found " + commitIds.size() + " commits for " + relativeFilePath);
            } else {
                // If no commits found with --follow, try without it
                System.out.println("[WARN] No commits found with --follow for: " + relativeFilePath + ". Trying without --follow...");
                cmd = "git log -n " + n + " --pretty=format:%H -- \"" + relativeFilePath + "\"";
                output = runGitCommand(cmd, baseRepoPath);

                if (output != null && !output.trim().isEmpty()) {
                    String[] commits = output.split("\n");
                    commitIds.addAll(Arrays.asList(commits));
                    System.out.println("[DEBUG] Found " + commitIds.size() + " commits without --follow");
                } else {
                    System.out.println("[WARN] Still no commits found for: " + relativeFilePath);
                }
            }
        } catch (Exception e) {
            System.err.println("[ERROR] Could not retrieve commits for " + relativeFilePath + ": " + e.getMessage());
            e.printStackTrace();
        }

        return commitIds;
    }

    /**
     * Get all commit history data for multiple files before checking out any of them.
     * Returns a map of file paths to their commits.
     */
    public Map<String, List<String>> getCommitHistoryForFiles(List<String> filePaths, int commitsPerFile) {
        Map<String, List<String>> fileCommits = new HashMap<>();

        for (String filePath : filePaths) {
            List<String> commits = getCommitsForFile(filePath, commitsPerFile);
            if (!commits.isEmpty()) {
                fileCommits.put(filePath, commits);
            }
        }

        return fileCommits;
    }

    /**
     * Checkout a specific commit
     */
    public boolean checkoutCommit(String commitId) {
        try {
            String output = runGitCommand("git checkout " + commitId, baseRepoPath);
            System.out.println("[DEBUG] Checked out commit: " + commitId);
            return true;
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to checkout commit " + commitId + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * Get the current branch
     */
    public String getCurrentBranch() {
        try {
            return runGitCommand("git rev-parse --abbrev-ref HEAD", baseRepoPath).trim();
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to get current branch: " + e.getMessage());
            return "unknown";
        }
    }

    /**
     * Restore to original branch
     */
    public boolean restoreOriginalBranch(String branch) {
        try {
            String output = runGitCommand("git checkout " + branch, baseRepoPath);
            System.out.println("[DEBUG] Restored to branch: " + branch);
            return true;
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to restore to branch " + branch + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * Converts a full path to one relative to the repo root.
     */
    public String getRelativeFilePath(String fullFilePath) {
        String relativePath = fullFilePath.replace(baseRepoPath, "").replace('\\', '/');
        return relativePath.startsWith("/") ? relativePath.substring(1) : relativePath;
    }

    /**
     * Run a git command
     */
    private String runGitCommand(String command, String repoPath) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder("bash", "-c", command);
        builder.directory(new File(repoPath));
        builder.redirectErrorStream(true);
        Process process = builder.start();
        InputStream inputStream = process.getInputStream();
        String output = new BufferedReader(new InputStreamReader(inputStream))
                .lines().collect(java.util.stream.Collectors.joining("\n"));
        process.waitFor();
        return output;
    }
}
