package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Integration test using a real repository and RefactoringMiner
 */
public class IntegrationTestForRefactorings {

    private static final String TEST_OUTPUT_FILE = "integration_test_output.json";
    private static final String REPO_PATH = "/Users/jesusvaladez/Desktop/RefactoringMiner 2/src/main/resources/ambari";
    private static final String TEST_FILE_PATH = "/Users/jesusvaladez/Desktop/RefactoringMiner 2/src/main/resources/ambari/ambari-server/src/test/java/org/apache/ambari/annotations/TransactionalLockTest.java";

    private OutputService outputService;
    private TraverseCommit traverseCommit;
    private JavaFileAnalyzer analyzer;


    // We want to test for quality of the program so we initialize the classes here
    @BeforeEach
    public void setup() throws Exception {
        System.out.println("Setting up integration test with real repository");
        System.out.println("Repository path: " + REPO_PATH);
        System.out.println("Test file: " + TEST_FILE_PATH);

        // Create the output service
        outputService = new OutputServiceImpl();

        // Create the real TraverseCommit instance with the actual repository
        traverseCommit = new TraverseCommit(REPO_PATH);

        // Initialize the analyzer
        analyzer = new JavaFileAnalyzer(outputService, traverseCommit, TEST_OUTPUT_FILE);

        // Initialize output file with JSON array opener
        try (java.io.FileWriter writer = new java.io.FileWriter(TEST_OUTPUT_FILE)) {
            writer.write("[\n");
        }
    }

    // Don't actually want to keep the JSON Output for this test
    @AfterEach
    public void cleanup() {
        // Delete test output file
        try {
            Files.deleteIfExists(Paths.get(TEST_OUTPUT_FILE));
            System.out.println("Test output file deleted");
        } catch (IOException e) {
            System.err.println("Could not delete test file: " + e.getMessage());
        }
    }

    // We want to check for every single little thing
    // Does JSON form? If it does, then it shouldn't be empty
    // Edit comment later
    @Test
    public void testRealFileAnalysis() throws Exception {
        System.out.println("\n==== Starting integration test with real file ====");

        // Run the analysis on the single file
        analyzer.analyze(TEST_FILE_PATH);

        // Close the JSON array properly
        try (java.io.FileWriter writer = new java.io.FileWriter(TEST_OUTPUT_FILE, true)) {
            // Remove trailing comma if present
            String content = Files.readString(Paths.get(TEST_OUTPUT_FILE));
            if (content.endsWith(",\n")) {
                content = content.substring(0, content.length() - 2) + "\n";
                Files.writeString(Paths.get(TEST_OUTPUT_FILE), content);
            }
            writer.write("]");
        }

        // Read the generated JSON file
        String jsonContent = Files.readString(Paths.get(TEST_OUTPUT_FILE));

        // Check if the JSON is properly formed and not empty
        assertFalse(jsonContent.isEmpty(), "JSON output should not be empty");
        assertTrue(jsonContent.startsWith("[") && jsonContent.endsWith("]"),
                "JSON output should be a valid array");

        System.out.println("\nGenerated JSON content (abbreviated):");
        System.out.println(jsonContent.length() > 500 ?
                jsonContent.substring(0, 500) + "..." : jsonContent);

        // Parse the JSON content
        JsonArray jsonArray = JsonParser.parseString(jsonContent).getAsJsonArray();

        // Validate the JSON structure
        assertFalse(jsonArray.isEmpty(), "JSON array should not be empty");

        // Count test methods and refactorings
        int testMethodCount = 0;
        //int methodsWithRefactorings = 0;

        // Validate each entry in the JSON file
        for (JsonElement element : jsonArray) {
            JsonObject jsonObject = element.getAsJsonObject();
            testMethodCount++;

            // Check that all required fields are present
            assertTrue(jsonObject.has("file_path"), "JSON should contain file_path");
            assertTrue(jsonObject.has("test_method_name"), "JSON should contain test_method_name");
            assertTrue(jsonObject.has("test_body"), "JSON should contain test_body");
            assertTrue(jsonObject.has("annotations"), "JSON should contain annotations");
            assertTrue(jsonObject.has("refactorings"), "JSON should contain refactorings");

            // Check that the file path is correct
            String filePath = jsonObject.get("file_path").getAsString();
            assertEquals(TEST_FILE_PATH, filePath, "File path should match the input test file path");



            // Print basic information about each test method
            System.out.println("\nTest Method #" + testMethodCount + ":");
            System.out.println("  Name: " + jsonObject.get("test_method_name").getAsString());
            System.out.println("  Annotations: " + jsonObject.get("annotations").getAsString());
            String bodyCode = jsonObject.get("test_body").getAsString();
            System.out.println("Body Preview: " + bodyCode);
        }

        // Print summary statistics
        System.out.println("\n==== Integration Test Summary ====");
        System.out.println("Total test methods analyzed: " + testMethodCount);
        // Assertion line
        // Whether there're assertions

        // Final assertions based on what we expect for this file
        assertTrue(testMethodCount > 0, "Should find at least one test method in the file");
        System.out.println("\nIntegration test completed successfully!");
    }
}
