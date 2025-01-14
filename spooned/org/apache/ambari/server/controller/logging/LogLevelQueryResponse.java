package org.apache.ambari.server.controller.logging;
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
public class LogLevelQueryResponse {
    private java.lang.String startIndex;

    private java.lang.String pageSize;

    private java.lang.String totalCount;

    private java.lang.String resultSize;

    private java.lang.String queryTimeMS;

    private java.util.List<org.apache.ambari.server.controller.logging.NameValuePair> nameValueList;

    @com.fasterxml.jackson.annotation.JsonProperty("startIndex")
    public java.lang.String getStartIndex() {
        return startIndex;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("startIndex")
    public void setStartIndex(java.lang.String startIndex) {
        this.startIndex = startIndex;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("pageSize")
    public java.lang.String getPageSize() {
        return pageSize;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("pageSize")
    public void setPageSize(java.lang.String pageSize) {
        this.pageSize = pageSize;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("totalCount")
    public java.lang.String getTotalCount() {
        return totalCount;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("totalCount")
    public void setTotalCount(java.lang.String totalCount) {
        this.totalCount = totalCount;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("resultSize")
    public java.lang.String getResultSize() {
        return resultSize;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("resultSize")
    public void setResultSize(java.lang.String resultSize) {
        this.resultSize = resultSize;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("queryTimeMS")
    public java.lang.String getQueryTimeMS() {
        return queryTimeMS;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("queryTimeMS")
    public void setQueryTimeMS(java.lang.String queryTimeMS) {
        this.queryTimeMS = queryTimeMS;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("vNameValues")
    public java.util.List<org.apache.ambari.server.controller.logging.NameValuePair> getNameValueList() {
        return nameValueList;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("vNameValues")
    public void setNameValueList(java.util.List<org.apache.ambari.server.controller.logging.NameValuePair> nameValueList) {
        this.nameValueList = nameValueList;
    }
}
