package org.apache.ambari.server.state.repository;
public class AvailableService {
    @com.fasterxml.jackson.annotation.JsonProperty("name")
    private java.lang.String name;

    @com.fasterxml.jackson.annotation.JsonProperty("display_name")
    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
    private java.lang.String displayName;

    private java.util.List<org.apache.ambari.server.state.repository.AvailableVersion> versions = new java.util.ArrayList<>();

    AvailableService(java.lang.String service, java.lang.String serviceDisplay) {
        name = service;
        displayName = serviceDisplay;
    }

    public java.lang.String getName() {
        return name;
    }

    public java.util.List<org.apache.ambari.server.state.repository.AvailableVersion> getVersions() {
        return versions;
    }
}
