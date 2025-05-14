package org.example;

import java.util.List;
import java.util.Map;

public interface OutputService {

    /**
     * Saves individual test result to a file
     * @param filePath Path to save the results
     * @param testResults Map containing test results data
     */
    void saveTestResultsToFile(String filePath, Map<String, Object> testResults);

    /**
     * Formats test output data into a structured format
     */
    Map<String, Object> formatTestOutput(String methodName, String body, String filePath,
                                         String annotations, int assertionAmount, String assertionType,
                                         List<Integer> assertionLines, String commitID);

    /**
     * Save all test results in the format from paste.txt
     */
    void saveAllTestResults(String filePath, Map<String, Map<String, List<Map<String, Object>>>> results);
}
