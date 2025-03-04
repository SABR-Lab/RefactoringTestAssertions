package org.example;

import spoon.Launcher;
import spoon.processing.AbstractProcessor;
import spoon.processing.ProcessingManager;
import spoon.reflect.factory.Factory;
import spoon.support.QueueProcessingManager;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class JavaFileAnalyzer {
    private static final Launcher launcher = new  Launcher();
    public static List<String> analyzeTests() {
        // Add the Java files to be parsed
        launcher.addInputResource(Paths.get(System.getProperty("user.dir"),
                "src", "main", "resources", "ambari").toString());

        // Configure the Spoon environment
        launcher.getEnvironment().setCommentEnabled(false);
        launcher.getEnvironment().setIgnoreDuplicateDeclarations(true);
        launcher.getEnvironment().setCopyResources(false);
        launcher.getEnvironment().setIgnoreSyntaxErrors(true);
        launcher.buildModel();

        // Create and run the assertion processor
        Assertion assertionProcessor = new Assertion();
        addRuleToAnalyze(assertionProcessor);

        // Return the paths of test files with assertions
        return new ArrayList<>(assertionProcessor.getTestFilePaths());
    }

    /**
     *
     * @param rule: add your rule
     */
    public static void addRuleToAnalyze(AbstractProcessor<?> rule) {
        final Factory factory = launcher.getFactory();
        final ProcessingManager processingManager = new QueueProcessingManager(factory);
        processingManager.addProcessor(rule);
        processingManager.process(factory.Class().getAll());
    }
}
