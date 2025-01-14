package org.apache.ambari.server.state.scheduler;
public class Batch {
    private final java.util.List<org.apache.ambari.server.state.scheduler.BatchRequest> batchRequests = new java.util.ArrayList<>();

    private org.apache.ambari.server.state.scheduler.BatchSettings batchSettings;

    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
    @com.fasterxml.jackson.annotation.JsonProperty("batch_requests")
    public java.util.List<org.apache.ambari.server.state.scheduler.BatchRequest> getBatchRequests() {
        return batchRequests;
    }

    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
    @com.fasterxml.jackson.annotation.JsonProperty("batch_settings")
    public org.apache.ambari.server.state.scheduler.BatchSettings getBatchSettings() {
        return batchSettings;
    }

    public void setBatchSettings(org.apache.ambari.server.state.scheduler.BatchSettings batchSettings) {
        this.batchSettings = batchSettings;
    }
}
