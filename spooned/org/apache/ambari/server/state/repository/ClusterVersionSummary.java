package org.apache.ambari.server.state.repository;
public class ClusterVersionSummary {
    @com.google.gson.annotations.SerializedName("services")
    @com.fasterxml.jackson.annotation.JsonProperty("services")
    private java.util.Map<java.lang.String, org.apache.ambari.server.state.repository.ServiceVersionSummary> m_services;

    private transient java.util.Set<java.lang.String> m_available = new java.util.HashSet<>();

    ClusterVersionSummary(java.util.Map<java.lang.String, org.apache.ambari.server.state.repository.ServiceVersionSummary> services) {
        m_services = services;
        for (java.util.Map.Entry<java.lang.String, org.apache.ambari.server.state.repository.ServiceVersionSummary> entry : services.entrySet()) {
            if (entry.getValue().isUpgrade()) {
                m_available.add(entry.getKey());
            }
        }
    }

    @com.fasterxml.jackson.annotation.JsonIgnore
    public java.util.Set<java.lang.String> getAvailableServiceNames() {
        return m_available;
    }
}
