package org.apache.ambari.server.controller.logging;
public class LogFileDefinitionInfo {
    private java.lang.String logFileName;

    private org.apache.ambari.server.controller.logging.LogFileType logFileType;

    private java.lang.String searchEngineURL;

    private java.lang.String logFileTailURL;

    public LogFileDefinitionInfo() {
    }

    protected LogFileDefinitionInfo(java.lang.String logFileName, org.apache.ambari.server.controller.logging.LogFileType logFileType, java.lang.String searchEngineURL, java.lang.String logFileTailURL) {
        this.logFileName = logFileName;
        this.logFileType = logFileType;
        this.searchEngineURL = searchEngineURL;
        this.logFileTailURL = logFileTailURL;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("name")
    public java.lang.String getLogFileName() {
        return logFileName;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("name")
    public void setLogFileName(java.lang.String logFileName) {
        this.logFileName = logFileName;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("type")
    public org.apache.ambari.server.controller.logging.LogFileType getLogFileType() {
        return logFileType;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("type")
    public void setLogFileType(org.apache.ambari.server.controller.logging.LogFileType logFileType) {
        this.logFileType = logFileType;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("searchEngineURL")
    public java.lang.String getSearchEngineURL() {
        return searchEngineURL;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("searchEngineURL")
    public void setSearchEngineURL(java.lang.String searchEngineURL) {
        this.searchEngineURL = searchEngineURL;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("logFileTailURL")
    public java.lang.String getLogFileTailURL() {
        return logFileTailURL;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("logFileTailURL")
    public void setLogFileTailURL(java.lang.String logFileTailURL) {
        this.logFileTailURL = logFileTailURL;
    }
}
