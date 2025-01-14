package org.apache.ambari.server.controller.logging;
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
public class HostLogFilesResponse {
    private java.util.Map<java.lang.String, java.util.List<java.lang.String>> hostLogFiles;

    @com.fasterxml.jackson.annotation.JsonProperty("hostLogFiles")
    public java.util.Map<java.lang.String, java.util.List<java.lang.String>> getHostLogFiles() {
        return hostLogFiles;
    }

    public void setHostLogFiles(java.util.Map<java.lang.String, java.util.List<java.lang.String>> hostLogFiles) {
        this.hostLogFiles = hostLogFiles;
    }
}
