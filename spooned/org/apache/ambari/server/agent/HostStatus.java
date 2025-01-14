package org.apache.ambari.server.agent;
public class HostStatus {
    org.apache.ambari.server.agent.HostStatus.Status status;

    java.lang.String cause;

    public HostStatus(org.apache.ambari.server.agent.HostStatus.Status status, java.lang.String cause) {
        super();
        this.status = status;
        this.cause = cause;
    }

    public HostStatus() {
        super();
    }

    public enum Status {

        HEALTHY,
        UNHEALTHY;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("status")
    public org.apache.ambari.server.agent.HostStatus.Status getStatus() {
        return status;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("status")
    public void setStatus(org.apache.ambari.server.agent.HostStatus.Status status) {
        this.status = status;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("cause")
    public java.lang.String getCause() {
        return cause;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("cause")
    public void setCause(java.lang.String cause) {
        this.cause = cause;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return (((("HostStatus{" + "status=") + status) + ", cause='") + cause) + '}';
    }
}
