package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gr.uom.java.xmi.diff.CodeRange;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringHandler;
import org.refactoringminer.rm1.GitHistoryRefactoringMinerImpl;
import org.refactoringminer.api.GitService;
import org.refactoringminer.util.GitServiceImpl;
import spoon.Launcher;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.filter.TypeFilter;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.*;

public class CommitAnalyzer {

    public static class MethodHistory {
        String methodSignature;
        List<Version> versions = new ArrayList<>();

        public static class Version {
            String commitId;
            String changeType;
            String body;
            String refactoring; // Optional field

            public Version(String commitId, String changeType, String body, String refactoring) {
                this.commitId = commitId;
                this.changeType = changeType;
                this.body = body;
                this.refactoring = refactoring;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        GitService gitService = new GitServiceImpl();
        Repository repo = gitService.cloneIfNotExists(
                "tmp/refactoring-toy-example",
                "https://github.com/danilofes/refactoring-toy-example.git");

        File repoDir = new File("/Users/jesusvaladez/Desktop/RefactoringMiner 2/tmp/refactoring-toy-example");
        File outputJson = new File("katherine.json");

        Map<String, MethodHistory> methodHistories = new HashMap<>();
        Map<String, String> lastBodies = new HashMap<>();

        try (Git git = Git.open(repoDir)) {
            List<RevCommit> commits = new ArrayList<>();
            git.log().call().forEach(commits::add);
           // Collections.reverse(commits); // Reverse to go from oldest to newest

            for (int i = 0; i < commits.size(); i++) {
                RevCommit commit = commits.get(i);
                String commitId = commit.getName();
                System.out.println("Analyzing commit: " + commitId);

                // Skip the root commit if necessary
                if (commit.getParentCount() == 0) {
                    System.out.println("Skipping initial commit: " + commitId);
                    continue;
                }

                git.checkout().setName(commitId).call();

                // Initialize Spoon for parsing the code
                Launcher launcher = new Launcher();
                launcher.addInputResource(new File(repoDir, "src").getAbsolutePath());
                launcher.getEnvironment().setNoClasspath(true);
                CtModel model = launcher.buildModel();

                Set<String> seenThisCommit = new HashSet<>();

                // Refactoring detection
                Map<String, String> refactoringMap = new HashMap<>();
                new GitHistoryRefactoringMinerImpl().detectAtCommit(
                        repo,
                        commitId,
                        new RefactoringHandler() {
                            @Override
                            public void handle(String commitId, List<Refactoring> refactorings) {
                                if (refactorings.isEmpty()) {
                                    System.out.println("No refactorings detected at commit: " + commitId);
                                }
                                System.out.println("Refactorings at " + commitId + ":");
                                for (Refactoring ref : refactorings) {
                                    System.out.println("  " + ref);
                                    for (CodeRange afterRange : ref.rightSide()) {
                                        refactoringMap.put(afterRange.getCodeElement(), ref.toString());
                                    }
                                }
                            }
                        });

                // Analyze methods in this commit
                for (CtMethod<?> method : model.getElements(new TypeFilter<>(CtMethod.class))) {
                    String signature = method.getDeclaringType().getQualifiedName() + "#" + method.getSignature();
                    String currentBody = method.toString();
                    seenThisCommit.add(signature);

                    String refType = refactoringMap.getOrDefault(method.getSimpleName(), null);

                    methodHistories.computeIfAbsent(signature, k -> {
                        MethodHistory h = new MethodHistory();
                        h.methodSignature = k;
                        return h;
                    });

                    // Handle added or modified methods
                    if (!lastBodies.containsKey(signature)) {
                        methodHistories.get(signature).versions.add(
                                new MethodHistory.Version(commitId, "ADDED", currentBody, refType));
                    } else if (!lastBodies.get(signature).equals(currentBody)) {
                        methodHistories.get(signature).versions.add(
                                new MethodHistory.Version(commitId, "MODIFIED", currentBody, refType));
                    } else {
                        continue;
                    }
                    lastBodies.put(signature, currentBody);
                }

                // Detect deleted methods
                for (String oldMethod : new HashSet<>(lastBodies.keySet())) {
                    if (!seenThisCommit.contains(oldMethod)) {
                        methodHistories.get(oldMethod).versions.add(
                                new MethodHistory.Version(commitId, "REMOVED", null, null));
                        lastBodies.remove(oldMethod);
                    }
                }
            }
        }

        // Write results to JSON file
        try (Writer writer = new FileWriter(outputJson)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(methodHistories.values(), writer);
        }

        System.out.println("History with refactorings written to: " + outputJson.getAbsolutePath());
    }
}
