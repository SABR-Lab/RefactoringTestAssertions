package org.example;

import java.util.List;
import java.util.Map;

public interface OutputService {

    /**
     * Saves test results to a file
     * @param filePath Path to save the results
     * @param testResults Map containing test results data
     */
    void saveTestResultsToFile(String filePath, Map<String, Object> testResults);

    /**
     * Formats test output data into a structured format
     * @param methodName Name of the test method
     * @param body Body content of the test method
     * @param filePath File path where the test is located
     * @param annotations Annotations of the test method
     * @param refactorings Refactorings related to the test
     * @return Formatted map of test data
     */
    Map<String, Object> formatTestOutput(String methodName, String body, String filePath,
                                         String annotations, int assertionAmount, String assertionType, List<Integer> assertionLines, String refactorings);
}
