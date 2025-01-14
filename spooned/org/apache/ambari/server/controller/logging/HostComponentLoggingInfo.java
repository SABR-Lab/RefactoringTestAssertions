package org.apache.ambari.server.controller.logging;
public class HostComponentLoggingInfo {
    private java.lang.String componentName;

    private java.util.List<org.apache.ambari.server.controller.logging.LogFileDefinitionInfo> listOfLogFileDefinitions;

    private java.util.List<org.apache.ambari.server.controller.logging.NameValuePair> listOfLogLevels;

    public HostComponentLoggingInfo() {
    }

    @com.fasterxml.jackson.annotation.JsonProperty("name")
    public java.lang.String getComponentName() {
        return componentName;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("name")
    public void setComponentName(java.lang.String componentName) {
        this.componentName = componentName;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("logs")
    public java.util.List<org.apache.ambari.server.controller.logging.LogFileDefinitionInfo> getListOfLogFileDefinitions() {
        return listOfLogFileDefinitions;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("logs")
    public void setListOfLogFileDefinitions(java.util.List<org.apache.ambari.server.controller.logging.LogFileDefinitionInfo> listOfLogFileDefinitions) {
        this.listOfLogFileDefinitions = listOfLogFileDefinitions;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("log_level_counts")
    public java.util.List<org.apache.ambari.server.controller.logging.NameValuePair> getListOfLogLevels() {
        return listOfLogLevels;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("log_level_counts")
    public void setListOfLogLevels(java.util.List<org.apache.ambari.server.controller.logging.NameValuePair> listOfLogLevels) {
        this.listOfLogLevels = listOfLogLevels;
    }
}
