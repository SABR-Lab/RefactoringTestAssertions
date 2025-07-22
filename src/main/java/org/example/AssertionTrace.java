package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Assertion traces details
 */
public class AssertionTrace {
    private String testMethodName;
    private String assertionType;
    private List<Object> parameters;
    private Object expectedValue;
    private Object actualValue;
    private boolean passed;
    private String exceptionMessage;
    private long executionTime;
    private int lineNumber;
    private String stackTrace;
    private String fileName;
    private Map<String, Object> metadata;

    public AssertionTrace() {
        this.parameters = new ArrayList<>();
        this.metadata = new HashMap<>();
    }

    // Getter methods
    public String getTestMethodName() {
        return testMethodName;
    }
    public void setTestMethodName(String testMethodName) {
        this.testMethodName = testMethodName;
    }

    public String getAssertionType() {
        return assertionType;
    }
    public void setAssertionType(String assertionType) {
        this.assertionType = assertionType;
    }

    public List<Object> getParameters() {
        return parameters;
    }
    public void setParameters(List<Object> parameters) {
        this.parameters = parameters;
    }

    public Object getExpectedValue() {
        return expectedValue;
    }
    public void setExpectedValue(Object expectedValue) {
        this.expectedValue = expectedValue;
    }

    public Object getActualValue() {
        return actualValue;
    }
    public void setActualValue(Object actualValue) {
        this.actualValue = actualValue;
    }

    public boolean isPassed() {
        return passed;
    }
    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }
    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public long getExecutionTime() {
        return executionTime;
    }
    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    public int getLineNumber() {
        return lineNumber;
    }
    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getStackTrace() {
        return stackTrace;
    }
    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }
    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return String.format("AssertionTrace{method='%s', type='%s', passed=%s, time=%dns}",
                testMethodName, assertionType, passed, executionTime);
    }
}
