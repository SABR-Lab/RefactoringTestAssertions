package org.apache.ambari.server.agent;
@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
public class RegistrationResponse extends org.apache.ambari.server.agent.stomp.StompResponse {
    @com.fasterxml.jackson.annotation.JsonProperty("response")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private org.apache.ambari.server.agent.RegistrationStatus response;

    @com.fasterxml.jackson.annotation.JsonProperty("alertDefinitionCommands")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private java.util.List<org.apache.ambari.server.agent.AlertDefinitionCommand> alertDefinitionCommands = new java.util.ArrayList<>();

    @com.fasterxml.jackson.annotation.JsonProperty("exitstatus")
    private int exitStatus;

    @com.fasterxml.jackson.annotation.JsonProperty("log")
    private java.lang.String log;

    @com.fasterxml.jackson.annotation.JsonProperty("id")
    @com.fasterxml.jackson.annotation.JsonAlias({ "id", "responseId" })
    private long responseId;

    @com.fasterxml.jackson.annotation.JsonProperty("recoveryConfig")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private org.apache.ambari.server.agent.RecoveryConfig recoveryConfig;

    @com.fasterxml.jackson.annotation.JsonProperty("agentConfig")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private java.util.Map<java.lang.String, java.lang.String> agentConfig;

    @com.fasterxml.jackson.annotation.JsonProperty("statusCommands")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private java.util.List<org.apache.ambari.server.agent.StatusCommand> statusCommands = null;

    @com.fasterxml.jackson.annotation.JsonIgnore
    public org.apache.ambari.server.agent.RegistrationStatus getResponseStatus() {
        return response;
    }

    public void setResponseStatus(org.apache.ambari.server.agent.RegistrationStatus response) {
        this.response = response;
    }

    public java.util.List<org.apache.ambari.server.agent.StatusCommand> getStatusCommands() {
        return statusCommands;
    }

    public void setStatusCommands(java.util.List<org.apache.ambari.server.agent.StatusCommand> statusCommands) {
        this.statusCommands = statusCommands;
    }

    public java.util.List<org.apache.ambari.server.agent.AlertDefinitionCommand> getAlertDefinitionCommands() {
        return alertDefinitionCommands;
    }

    public void setAlertDefinitionCommands(java.util.List<org.apache.ambari.server.agent.AlertDefinitionCommand> commands) {
        alertDefinitionCommands = commands;
    }

    public long getResponseId() {
        return responseId;
    }

    public void setResponseId(long responseId) {
        this.responseId = responseId;
    }

    public void setExitstatus(int exitstatus) {
        this.exitStatus = exitstatus;
    }

    public void setLog(java.lang.String log) {
        this.log = log;
    }

    public org.apache.ambari.server.agent.RecoveryConfig getRecoveryConfig() {
        return recoveryConfig;
    }

    public void setRecoveryConfig(org.apache.ambari.server.agent.RecoveryConfig recoveryConfig) {
        this.recoveryConfig = recoveryConfig;
    }

    public java.util.Map<java.lang.String, java.lang.String> getAgentConfig() {
        return agentConfig;
    }

    public void setAgentConfig(java.util.Map<java.lang.String, java.lang.String> agentConfig) {
        this.agentConfig = agentConfig;
    }

    @java.lang.Override
    public java.lang.String toString() {
        java.lang.StringBuilder buffer = new java.lang.StringBuilder("RegistrationResponse{");
        buffer.append("response=").append(response);
        buffer.append(", responseId=").append(responseId);
        buffer.append(", statusCommands=").append(statusCommands);
        buffer.append(", alertDefinitionCommands=").append(alertDefinitionCommands);
        buffer.append(", recoveryConfig=").append(recoveryConfig);
        buffer.append(", agentConfig=").append(agentConfig);
        buffer.append('}');
        return buffer.toString();
    }
}
