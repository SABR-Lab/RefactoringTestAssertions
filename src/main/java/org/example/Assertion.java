package org.example;

import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtType;

import java.util.List;

/**
 * Utility class for test method analysis
 */
public final class Assertion {

    // Constructor made private since this is a utility class
    private Assertion() {
        // Prevent instantiation
    }

    /**
     * Checks if a method is a test method based on naming conventions or annotations
     */
    public static boolean isTestMethod(CtMethod<?> method) {
        return method.getAnnotations().stream()
                .anyMatch(annot -> annot.getAnnotationType().getSimpleName().equals("Test"))
                || method.getSimpleName().startsWith("test")
                || method.getSimpleName().endsWith("Test");
    }

    /**
     * Checks if a test method contains assertions
     */
    public static boolean testWithAssertion(CtMethod<?> method) {
        if (method.getBody() != null) {
            return method.getBody().getStatements().stream()
                    .anyMatch(statement -> statement.toString().contains("assert"));
        }
        return false;
    }

    /**
     * Gets the file path of a method
     */
    public static String getFilePath(CtMethod<?> method) {
        CtType<?> parentClass = method.getParent(CtType.class);
        return parentClass.getPosition().getFile().getAbsolutePath();
    }

    /**
     * Gets the annotations of a method as a string
     */
    public static String getAnnotation(CtMethod<?> method) {
        return method.getAnnotations().stream()
                .map(Object::toString)
                .findFirst()
                .orElse("No Annotations");
    }

    /**
     * Gets the body code of a method
     */
    public static String getBodyCode(CtMethod<?> method) {
        return method.getBody() != null ? method.getBody().toString() : "No Body";
    }

    /**
     * Gets the method signature
     */
    public static String getMethodSignature(CtMethod<?> method) {
        return method.getSimpleName() + method.getParameters().toString();
    }

    /**
     * Gets the number of Assertions in the method body
     */
    public static int getAssertionAmount(CtMethod<?> method) {
        if (method.getBody() != null) {
            return ((int) method.getBody().getStatements().stream()
                    .filter(statement -> statement.toString().contains("assert"))
                    .count());
        }
        return 0;
    }

    /**
     * Gets the Assertion type
     */
    public static String getAssertionType(CtMethod<?> method) {
        if (method.getBody() != null) {
            return method.getBody().getStatements().stream()
                    .filter(statement -> statement.toString().contains("assert"))
                    .map(statement -> statement.toString().trim())
                    .distinct()
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("No Assertions");
        }
        return "No Assertions";
    }

    /**
     * Gets the line numbers of where the assertions are in file
     */
    public static List<Integer> getAssertionLineNumbers(CtMethod<?> method) {
        if (method.getBody() != null) {
            return method.getBody().getStatements().stream()
                    .filter(statement -> statement.toString().startsWith("assert"))
                    . map(statement -> statement.getPosition().getLine())
                    .filter(line -> line > 0)
                    .distinct()
                    .sorted()
                    .toList();
        }
        return List.of();
    }
}
