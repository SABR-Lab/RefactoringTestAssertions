package org.apache.ambari.server.controller;
public class AlertDefinitionResponse {
    private java.lang.String serviceName = null;

    private java.lang.String componentName = null;

    private java.lang.String name = null;

    private java.lang.String label = null;

    private java.lang.String description = null;

    private java.lang.Long definitionId;

    private boolean enabled = true;

    private org.apache.ambari.server.state.alert.SourceType sourceType;

    @com.fasterxml.jackson.annotation.JsonProperty("id")
    public java.lang.Long getDefinitionId() {
        return definitionId;
    }

    public void setDefinitionId(java.lang.Long definitionId) {
        this.definitionId = definitionId;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("service_name")
    public java.lang.String getServiceName() {
        return serviceName;
    }

    public void setServiceName(java.lang.String name) {
        serviceName = name;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("component_name")
    public java.lang.String getComponentName() {
        return componentName;
    }

    public void setComponentName(java.lang.String name) {
        componentName = name;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("name")
    public java.lang.String getName() {
        return name;
    }

    public void setName(java.lang.String definitionName) {
        name = definitionName;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("label")
    public java.lang.String getLabel() {
        return label;
    }

    public void setLabel(java.lang.String definitionLabel) {
        label = definitionLabel;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("description")
    public java.lang.String getDescription() {
        return description;
    }

    public void setDescription(java.lang.String definitionDescription) {
        description = definitionDescription;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("enabled")
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("source_type")
    public org.apache.ambari.server.state.alert.SourceType getSourceType() {
        return sourceType;
    }

    public void setSourceType(org.apache.ambari.server.state.alert.SourceType sourceType) {
        this.sourceType = sourceType;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return name;
    }

    public static org.apache.ambari.server.controller.AlertDefinitionResponse coerce(org.apache.ambari.server.orm.entities.AlertDefinitionEntity entity) {
        if (null == entity) {
            return null;
        }
        org.apache.ambari.server.controller.AlertDefinitionResponse response = new org.apache.ambari.server.controller.AlertDefinitionResponse();
        response.setDefinitionId(entity.getDefinitionId());
        response.setComponentName(entity.getComponentName());
        response.setLabel(entity.getLabel());
        response.setDescription(entity.getDescription());
        response.setName(entity.getDefinitionName());
        response.setServiceName(entity.getServiceName());
        response.setEnabled(entity.getEnabled());
        response.setSourceType(entity.getSourceType());
        return response;
    }
}
