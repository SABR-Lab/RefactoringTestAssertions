package org.apache.ambari.server.controller.logging;
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
public class LogLineResult {
    private final java.util.Map<java.lang.String, java.lang.String> mapOfLogLineProperties = new java.util.HashMap<>();

    private java.lang.String clusterName;

    private java.lang.String logMethod;

    private java.lang.String logLevel;

    private java.lang.String eventCount;

    private java.lang.String ipAddress;

    private java.lang.String componentType;

    private java.lang.String sequenceNumber;

    private java.lang.String logFilePath;

    private java.lang.String sourceFile;

    private java.lang.String sourceFileLineNumber;

    private java.lang.String hostName;

    private java.lang.String logMessage;

    private java.lang.String loggerName;

    private java.lang.String id;

    private java.lang.String messageMD5;

    private java.lang.String logTime;

    private java.lang.String eventMD5;

    private java.lang.String logFileLineNumber;

    private java.lang.String ttl;

    private java.lang.String expirationTime;

    private java.lang.String version;

    private java.lang.String thread_name;

    public LogLineResult() {
    }

    public LogLineResult(java.util.Map<java.lang.String, java.lang.String> propertiesMap) {
        mapOfLogLineProperties.putAll(propertiesMap);
    }

    public java.lang.String getProperty(java.lang.String propertyName) {
        return mapOfLogLineProperties.get(propertyName);
    }

    public boolean doesPropertyExist(java.lang.String propertyName) {
        return mapOfLogLineProperties.containsKey(propertyName);
    }

    @com.fasterxml.jackson.annotation.JsonProperty("cluster")
    public java.lang.String getClusterName() {
        return clusterName;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("cluster")
    public void setClusterName(java.lang.String clusterName) {
        this.clusterName = clusterName;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("method")
    public java.lang.String getLogMethod() {
        return logMethod;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("method")
    public void setLogMethod(java.lang.String logMethod) {
        this.logMethod = logMethod;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("level")
    public java.lang.String getLogLevel() {
        return logLevel;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("level")
    public void setLogLevel(java.lang.String logLevel) {
        this.logLevel = logLevel;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("event_count")
    public java.lang.String getEventCount() {
        return eventCount;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("event_count")
    public void setEventCount(java.lang.String eventCount) {
        this.eventCount = eventCount;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("ip")
    public java.lang.String getIpAddress() {
        return ipAddress;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("ip")
    public void setIpAddress(java.lang.String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("type")
    public java.lang.String getComponentType() {
        return componentType;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("type")
    public void setComponentType(java.lang.String componentType) {
        this.componentType = componentType;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("seq_num")
    public java.lang.String getSequenceNumber() {
        return sequenceNumber;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("seq_num")
    public void setSequenceNumber(java.lang.String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("path")
    public java.lang.String getLogFilePath() {
        return logFilePath;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("path")
    public void setLogFilePath(java.lang.String logFilePath) {
        this.logFilePath = logFilePath;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("file")
    public java.lang.String getSourceFile() {
        return sourceFile;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("file")
    public void setSourceFile(java.lang.String sourceFile) {
        this.sourceFile = sourceFile;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("line_number")
    public java.lang.String getSourceFileLineNumber() {
        return sourceFileLineNumber;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("line_number")
    public void setSourceFileLineNumber(java.lang.String sourceFileLineNumber) {
        this.sourceFileLineNumber = sourceFileLineNumber;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("host")
    public java.lang.String getHostName() {
        return hostName;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("host")
    public void setHostName(java.lang.String hostName) {
        this.hostName = hostName;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("log_message")
    public java.lang.String getLogMessage() {
        return logMessage;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("log_message")
    public void setLogMessage(java.lang.String logMessage) {
        this.logMessage = logMessage;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("logger_name")
    public java.lang.String getLoggerName() {
        return loggerName;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("logger_name")
    public void setLoggerName(java.lang.String loggerName) {
        this.loggerName = loggerName;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("id")
    public java.lang.String getId() {
        return id;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("id")
    public void setId(java.lang.String id) {
        this.id = id;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("message_md5")
    public java.lang.String getMessageMD5() {
        return messageMD5;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("message_md5")
    public void setMessageMD5(java.lang.String messageMD5) {
        this.messageMD5 = messageMD5;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("logtime")
    public java.lang.String getLogTime() {
        return logTime;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("logtime")
    public void setLogTime(java.lang.String logTime) {
        this.logTime = logTime;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("event_md5")
    public java.lang.String getEventMD5() {
        return eventMD5;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("event_md5")
    public void setEventMD5(java.lang.String eventMD5) {
        this.eventMD5 = eventMD5;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("logfile_line_number")
    public java.lang.String getLogFileLineNumber() {
        return logFileLineNumber;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("logfile_line_number")
    public void setLogFileLineNumber(java.lang.String logFileLineNumber) {
        this.logFileLineNumber = logFileLineNumber;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("_ttl_")
    public java.lang.String getTtl() {
        return ttl;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("_ttl_")
    public void setTtl(java.lang.String ttl) {
        this.ttl = ttl;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("_expire_at_")
    public java.lang.String getExpirationTime() {
        return expirationTime;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("_expire_at_")
    public void setExpirationTime(java.lang.String expirationTime) {
        this.expirationTime = expirationTime;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("_version_")
    public java.lang.String getVersion() {
        return version;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("_version_")
    public void setVersion(java.lang.String version) {
        this.version = version;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("thread_name")
    public java.lang.String getThreadName() {
        return thread_name;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("thread_name")
    public void setThreadName(java.lang.String thread_name) {
        this.thread_name = thread_name;
    }
}
