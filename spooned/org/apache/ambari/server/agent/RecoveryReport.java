package org.apache.ambari.server.agent;
public class RecoveryReport {
    private java.lang.String summary = "DISABLED";

    private java.util.List<org.apache.ambari.server.agent.ComponentRecoveryReport> componentReports = new java.util.ArrayList<>();

    @com.fasterxml.jackson.annotation.JsonProperty("summary")
    public java.lang.String getSummary() {
        return summary;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("summary")
    public void setSummary(java.lang.String summary) {
        this.summary = summary;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("component_reports")
    public java.util.List<org.apache.ambari.server.agent.ComponentRecoveryReport> getComponentReports() {
        return componentReports;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("component_reports")
    public void setComponentReports(java.util.List<org.apache.ambari.server.agent.ComponentRecoveryReport> componentReports) {
        this.componentReports = componentReports;
    }

    @java.lang.Override
    public java.lang.String toString() {
        java.lang.String componentReportsStr = "[]";
        if (componentReports != null) {
            componentReportsStr = java.util.Arrays.toString(componentReports.toArray());
        }
        return ((((("RecoveryReport{" + "summary='") + summary) + '\'') + ", component_reports='") + componentReportsStr) + "'}";
    }
}
