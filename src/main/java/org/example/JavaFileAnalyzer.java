package org.example;

import spoon.Launcher;
import spoon.processing.AbstractProcessor;
import spoon.processing.ProcessingManager;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.factory.Factory;
import spoon.support.QueueProcessingManager;

import java.util.*;

public class JavaFileAnalyzer {
    private final Launcher launcher;
    private final OutputService outputService;
    private final TraverseCommit traverseCommit;
    private final String outputFilePath;

    public JavaFileAnalyzer(OutputService outputService, TraverseCommit traverseCommit, String outputFilePath) {
        this.launcher = new Launcher();
        this.outputService = outputService;
        this.traverseCommit = traverseCommit;
        this.outputFilePath = outputFilePath;
    }

    public void analyze(String repositoryPath) {
        // Add the Java files to be parsed
        launcher.addInputResource(repositoryPath);

        // Configure the Spoon environment
        launcher.getEnvironment().setCommentEnabled(false);
        launcher.getEnvironment().setIgnoreDuplicateDeclarations(true);
        launcher.getEnvironment().setCopyResources(false);
        launcher.getEnvironment().setIgnoreSyntaxErrors(true);
        launcher.buildModel();

        // Create the processor
        TestMethodProcessor processor = new TestMethodProcessor(outputService, traverseCommit, outputFilePath);
        addRuleToAnalyze(processor);
    }

    public void addRuleToAnalyze(AbstractProcessor<?> rule) {
        final Factory factory = launcher.getFactory();
        final ProcessingManager processingManager = new QueueProcessingManager(factory);
        processingManager.addProcessor(rule);
        processingManager.process(factory.Class().getAll());
    }

    /**
     * Processor that handles test methods one at a time, with refactoring analysis
     */
    private static class TestMethodProcessor extends AbstractProcessor<CtMethod<?>> {
        private final OutputService outputService;
        private final TraverseCommit traverseCommit;
        private final String outputFilePath;

        public TestMethodProcessor(OutputService outputService, TraverseCommit traverseCommit, String outputFilePath) {
            this.outputService = outputService;
            this.traverseCommit = traverseCommit;
            this.outputFilePath = outputFilePath;
        }

        @Override
        public void process(CtMethod<?> method) {
            if (Assertion.isTestMethod(method)) {
                try {
                    // STEP 1: Get test method information using Assertion class
                    String methodName = Assertion.getMethodSignature(method);
                    String annotations = Assertion.getAnnotation(method);
                    String bodyCode = method.getBody() != null ? Assertion.getBodyCode(method) : "No Body";
                    String filePath = Assertion.getFilePath(method);
                    // NEW
                    int assertionAmount = Assertion.getAssertionAmount(method);
                    String assertionType = Assertion.getAssertionType(method);
                    List<Integer> assertionLines = Assertion.getAssertionLineNumbers(method);

                    System.out.println("Processing test method: " + methodName + " in " + filePath);

                    // STEP 2: Get commit IDs for this file from TraverseCommit claas
                    List<String> commitIds = traverseCommit.getCommitIdsForTestFiles(
                            Collections.singletonList(filePath));

                    // STEP 3: Run RefactoringMiner on those commit IDs to get refactorings from TraverseCommit class
                    String refactorings = "No refactorings found";
                    if (!commitIds.isEmpty()) {
                        // Get refactorings for the file's commits
                        refactorings = traverseCommit.getRefactoringsForCommits(
                                commitIds, filePath);
                    }

                    // STEP 4: Save complete test information to JSON file (dependency inject to Output and its interface)

                    Map<String, Object> formattedResult = outputService.formatTestOutput(
                            methodName, bodyCode, filePath, annotations, assertionAmount, assertionType, assertionLines, refactorings);
                    outputService.saveTestResultsToFile(outputFilePath, formattedResult);

                    System.out.println("Completed processing for: " + methodName);
                } catch (Exception e) {
                    System.err.println("Error processing method: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }
}
