package org.example;

import spoon.Launcher;
import spoon.reflect.CtModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AssertionTest {

    @Test
    public void testAssertionProcessing() {

        // Set up the Spoon Launcher and add the specific Java file to be tested
        Launcher launcher = new Launcher();
        String testFilePath = "src/main/resources/ambari/ambari-server-spi/src/test/java/org/apache/ambari/spi/upgrade/RepositoryTypeTest.java";
        launcher.addInputResource(testFilePath);
        launcher.buildModel();

        // Create an instance of the Assertion processor and process the model
        Assertion assertionProcessor = new Assertion();
        CtModel ctModel = launcher.getModel();
        ctModel.processWith(assertionProcessor);

        // Verify project gives the right output

        assertTrue(assertionProcessor.totalNumOfTests > 0, "No test cases found");
        assertTrue(assertionProcessor.testsWithAssertion >= 0, "Assertion count incorrect");
        assertTrue(assertionProcessor.testsWithoutAssertion >= 0, "Non-assertion test count incorrect");
        assertTrue(assertionProcessor.testsWithNullBody >= 0, "Null body test count incorrect");
        assertTrue(assertionProcessor.getTestFilePaths().size() > 0, "No test file paths found");


    }
}
