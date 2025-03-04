package org.example;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtType;

import java.util.*;


public final class Assertion extends AbstractProcessor<CtMethod<?>> {

    private static final Set<String> testFilePaths = new HashSet<>();

    public static int totalNumOfTests = 0;
    public static int testsWithAssertion = 0;
    public static int testsWithoutAssertion = 0;
    public static int testsWithNullBody = 0;

    public Assertion() {
        // This is to prevent instantiation
    }

    @Override
    public void process(CtMethod<?> method) {
        if (isTestMethod(method)) {
            totalNumOfTests++;

            // File path
            CtType<?> parentClass = method.getParent(CtType.class);
            String filePath = parentClass.getPosition().getFile().getAbsolutePath();
            testFilePaths.add(filePath);

            System.out.println("Processing test method: " + getMethodSignature(method));
            System.out.println("Annotation: " + getAnnotation(method));

            // For output class
            String bodyCode = method.getBody() != null ? getBodyCode(method) : "No Body";
            String methodName = getMethodSignature(method);
            Map<String, Object> formattedResult = Output.formatTestOutput(methodName, bodyCode, filePath);
            Output.saveTestResultsToFile("test_analysis_results.json", formattedResult);

            if (method.getBody() == null) {
                testsWithNullBody++;
                System.out.println();
            } else if (testWithAssertion(method)){
                testsWithAssertion++;
                System.out.println("Body code: " + getBodyCode(method));
            } else {
                testsWithoutAssertion++;
                System.out.println("Body Code: No assertions");
            }
        }
    }

    public static boolean isTestMethod(CtMethod<?> method) {
        return method.getAnnotations().stream()
                .anyMatch(annot -> annot.getAnnotationType().getSimpleName().equals("Test")
                        || method.getSimpleName().startsWith("test") ||
                        method.getSimpleName().endsWith("Test"));
    }

    public static boolean testWithAssertion(CtMethod<?> method) {
        if (method.getBody() != null) {
            return method.getBody().getStatements().stream()
                    .anyMatch(statement -> statement.toString().contains("assert"));
        }
        return false;
    }

    @Override
    public void processingDone() {
        assertionOutput();
    }

    public void assertionOutput() {
        // Purpose: Print test totals
        System.out.println("Total Number of Tests: " + totalNumOfTests);
        System.out.println("Tests with Assertion: " + testsWithAssertion);
        System.out.println("Tests without Assertion: " + testsWithoutAssertion);
        System.out.println("Tests with Null Body: " + testsWithNullBody);

        // Debug: Print collected file paths
        System.out.println("[DEBUG] Collected test file paths:");
        for (String path : testFilePaths) {
            System.out.println("[DEBUG] Test file: " + path);
        }
    }

    public static Set<String> getTestFilePaths() {
        return new HashSet<>(testFilePaths);
    }

    public static String getFilePath(CtMethod<?> method) {
        CtType<?> parentClass = method.getParent(CtType.class);
        return parentClass.getPosition().getFile().getAbsolutePath();
    }

    // Method to store annotations of test method
    public static String getAnnotation(CtMethod<?> method) {
        return method.getAnnotations().stream().
                map(Object::toString)
                .findFirst()
                .orElse("No Annotations");
    }

    // Method to get body code of the test method
    public static String getBodyCode(CtMethod<?> method) {
        return method.getBody() != null ? method.getBody().toString() : "No Body";
    }

    // Method to get method signature of test method
    public static String getMethodSignature(CtMethod<?> method) {
        return method.getSimpleName() + method.getParameters().toString();
    }
}
