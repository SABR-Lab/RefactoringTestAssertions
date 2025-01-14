package org.apache.ambari.server.api.services.stackadvisor;
public abstract class StackAdvisorResponse {
    private int id;

    @com.fasterxml.jackson.annotation.JsonProperty("Versions")
    private org.apache.ambari.server.api.services.stackadvisor.StackAdvisorResponse.Version version;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public org.apache.ambari.server.api.services.stackadvisor.StackAdvisorResponse.Version getVersion() {
        return version;
    }

    public void setVersion(org.apache.ambari.server.api.services.stackadvisor.StackAdvisorResponse.Version version) {
        this.version = version;
    }

    public static class Version {
        @com.fasterxml.jackson.annotation.JsonProperty("stack_name")
        private java.lang.String stackName;

        @com.fasterxml.jackson.annotation.JsonProperty("stack_version")
        private java.lang.String stackVersion;

        public java.lang.String getStackName() {
            return stackName;
        }

        public void setStackName(java.lang.String stackName) {
            this.stackName = stackName;
        }

        public java.lang.String getStackVersion() {
            return stackVersion;
        }

        public void setStackVersion(java.lang.String stackVersion) {
            this.stackVersion = stackVersion;
        }
    }
}
