package org.apache.ambari.server.controller.internal;
public class RequestResourceFilter implements org.apache.ambari.server.controller.ApiModel {
    private java.lang.String serviceName;

    private java.lang.String componentName;

    private final java.util.List<java.lang.String> hostNames = new java.util.ArrayList<>();

    public RequestResourceFilter() {
    }

    public RequestResourceFilter(java.lang.String serviceName, java.lang.String componentName, java.util.List<java.lang.String> hostNames) {
        this.serviceName = serviceName;
        this.componentName = componentName;
        if (hostNames != null) {
            this.hostNames.addAll(hostNames);
        }
    }

    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
    @com.fasterxml.jackson.annotation.JsonProperty("service_name")
    public java.lang.String getServiceName() {
        return serviceName;
    }

    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
    @com.fasterxml.jackson.annotation.JsonProperty("component_name")
    public java.lang.String getComponentName() {
        return componentName;
    }

    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
    @com.fasterxml.jackson.annotation.JsonProperty("hosts")
    public java.util.List<java.lang.String> getHostNames() {
        return hostNames;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return (((((((("RequestResourceFilter{" + "serviceName='") + serviceName) + '\'') + ", componentName='") + componentName) + '\'') + ", hostNames=") + hostNames) + '}';
    }
}
