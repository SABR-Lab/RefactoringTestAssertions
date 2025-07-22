package org.example;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.eclipse.jdt.core.IMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Has all traces for a certain commit
 */
public class AssertionTraceResults {
    private String commitId;
    private String filePath;
    private List<AssertionTrace> traces;
    private long totalExecutionTime;
    private int totalAssertions;
    private int passedAssertions;
    private int failedAssertions;
    private Map<String, Integer> assertionTypeBreakdown;
    private Map<String, Object> metadata;

    public AssertionTraceResults(String commitId, List<AssertionTrace> traces) {
        this.commitId = commitId;
        this.traces = traces;
        this.assertionTypeBreakdown = new HashMap<>();
        this.metadata = new HashMap<>();
        calculatedStatistics();
    }

    public AssertionTraceResults(String commitId, String filePath, List<AssertionTrace> traces) {
        this.commitId = commitId;
        this.filePath = filePath;
        this.traces = traces;
        this.assertionTypeBreakdown = new HashMap<>();
        this.metadata = new HashMap<>();
        calculatedStatistics();
    }

    private void calculatedStatistics() {
        this.totalAssertions = traces.size();
        this.passedAssertions = (int) traces.stream().filter(AssertionTrace::isPassed).count();
        this.failedAssertions = totalAssertions - passedAssertions;
        this.totalExecutionTime = traces.stream().mapToLong(AssertionTrace::getExecutionTime).sum();

        // Assertion type impl
        for (AssertionTrace trace : traces) {
            String type = trace.getAssertionType();
            if (type != null) {
                assertionTypeBreakdown.put(type, assertionTypeBreakdown.getOrDefault(type, 0) + 1);
            }
        }
    }

    // Getter methods
    public String getCommitId() {
        return commitId;
    }
    public String getFilePath() {
        return filePath;
    }
    public List<AssertionTrace> getTraces() {
        return traces;
    }
    public int getTotalAssertions() {
        return totalAssertions;
    }
    public int getPassedAssertions() {
        return passedAssertions;
    }
    public int getFailedAssertions() {
        return failedAssertions;
    }
    public long getTotalExecutionTime() {
        return totalExecutionTime;
    }
    public Map<String, Integer> getAssertionTypeBreakdown() {
        return assertionTypeBreakdown;
    }
    public Map<String, Object> getMetadata() {
        return metadata;
    }

    // Setter methods
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return String.format("Assertion Trace Result{commit='%s', total='%d', passed=%d, failed=%d",
                commitId, totalAssertions, passedAssertions, failedAssertions);
    }
}
