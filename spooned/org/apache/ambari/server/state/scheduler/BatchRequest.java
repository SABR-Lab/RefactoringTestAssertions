package org.apache.ambari.server.state.scheduler;
public class BatchRequest implements java.lang.Comparable<org.apache.ambari.server.state.scheduler.BatchRequest> {
    private java.lang.Long orderId;

    private java.lang.Long requestId;

    private org.apache.ambari.server.state.scheduler.BatchRequest.Type type;

    private java.lang.String uri;

    private java.lang.String body;

    private java.lang.String status;

    private java.lang.Integer returnCode;

    private java.lang.String responseMsg;

    @com.fasterxml.jackson.annotation.JsonProperty("order_id")
    public java.lang.Long getOrderId() {
        return orderId;
    }

    public void setOrderId(java.lang.Long orderId) {
        this.orderId = orderId;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("request_id")
    public java.lang.Long getRequestId() {
        return requestId;
    }

    public void setRequestId(java.lang.Long requestId) {
        this.requestId = requestId;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("request_type")
    public java.lang.String getType() {
        return type.name();
    }

    public void setType(org.apache.ambari.server.state.scheduler.BatchRequest.Type type) {
        this.type = type;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("request_uri")
    public java.lang.String getUri() {
        return uri;
    }

    public void setUri(java.lang.String uri) {
        this.uri = uri;
    }

    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
    @com.fasterxml.jackson.annotation.JsonProperty("request_body")
    public java.lang.String getBody() {
        return body;
    }

    public void setBody(java.lang.String body) {
        this.body = body;
    }

    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
    @com.fasterxml.jackson.annotation.JsonProperty("request_status")
    public java.lang.String getStatus() {
        return status;
    }

    public void setStatus(java.lang.String status) {
        this.status = status;
    }

    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
    @com.fasterxml.jackson.annotation.JsonProperty("return_code")
    public java.lang.Integer getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(java.lang.Integer returnCode) {
        this.returnCode = returnCode;
    }

    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
    @com.fasterxml.jackson.annotation.JsonProperty("response_message")
    public java.lang.String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(java.lang.String responseMsg) {
        this.responseMsg = responseMsg;
    }

    @java.lang.Override
    public int compareTo(org.apache.ambari.server.state.scheduler.BatchRequest batchRequest) {
        return this.orderId.compareTo(batchRequest.getOrderId());
    }

    public enum Type {

        PUT,
        POST,
        DELETE;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return ((((((("BatchRequest {" + "orderId=") + orderId) + ", type=") + type) + ", uri='") + uri) + '\'') + '}';
    }
}
