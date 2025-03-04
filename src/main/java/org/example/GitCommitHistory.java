package org.example;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;

public class GitCommitHistory {
    public static void main(String[] args) {
        String repoPath = "/Users/jesusvaladez/Desktop/RefactoringMiner 2/src/main/resources/ambari/";
        String filePath = "ambari-server-spi/src/test/java/org/apache/ambari/spi/upgrade/RepositoryTypeTest.java";

        try {
            FileRepositoryBuilder repositoryBuilder = new FileRepositoryBuilder();
            Repository repository = repositoryBuilder.setGitDir(new File(repoPath, ".git"))
                    .readEnvironment()
                    .findGitDir()
                    .build();

            Git git = new Git(repository);
            Iterable<RevCommit> commits = git.log()
                    .addPath(filePath)
                    .call();

            System.out.println("Commit IDs for file: " + filePath);
            for (RevCommit commit : commits) {
                System.out.println(commit.getId().getName());
            }
            git.close();

        } catch (IOException | GitAPIException e) {
            e.printStackTrace();
        }
    }
}
