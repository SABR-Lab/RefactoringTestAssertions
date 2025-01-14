package org.apache.ambari.server.agent;
public class ComponentRecoveryReport {
    private java.lang.String name;

    private int numAttempts;

    private boolean limitReached;

    @com.fasterxml.jackson.annotation.JsonProperty("name")
    public java.lang.String getName() {
        return name;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("name")
    public void setName(java.lang.String name) {
        this.name = name;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("num_attempts")
    public int getNumAttempts() {
        return numAttempts;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("num_attempts")
    public void setNumAttempts(int numAttempts) {
        this.numAttempts = numAttempts;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("limit_reached")
    public boolean getLimitReached() {
        return limitReached;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("limit_reached")
    public void setLimitReached(boolean limitReached) {
        this.limitReached = limitReached;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return ((((((((("ComponentRecoveryReport{" + "name='") + name) + '\'') + ", numFailures='") + numAttempts) + '\'') + ", limitReached='") + limitReached) + '\'') + '}';
    }
}
