package org.apache.ambari.server.state.theme;
import io.swagger.annotations.ApiModelProperty;
@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigCondition implements org.apache.ambari.server.controller.ApiModel {
    @com.fasterxml.jackson.annotation.JsonProperty("configs")
    private java.util.List<java.lang.String> configs;

    @com.fasterxml.jackson.annotation.JsonProperty("resource")
    private java.lang.String resource;

    @com.fasterxml.jackson.annotation.JsonProperty("if")
    private java.lang.String ifLabel;

    @com.fasterxml.jackson.annotation.JsonProperty("then")
    private org.apache.ambari.server.state.theme.ConfigCondition.ConfigConditionResult then;

    @com.fasterxml.jackson.annotation.JsonProperty("else")
    private org.apache.ambari.server.state.theme.ConfigCondition.ConfigConditionResult elseLabel;

    @io.swagger.annotations.ApiModelProperty(name = "configs")
    public java.util.List<java.lang.String> getConfigs() {
        return configs;
    }

    public void setConfigs(java.util.List<java.lang.String> configs) {
        this.configs = configs;
    }

    @io.swagger.annotations.ApiModelProperty(name = "if")
    public java.lang.String getIfLabel() {
        return ifLabel;
    }

    public void setIfLabel(java.lang.String ifLabel) {
        this.ifLabel = ifLabel;
    }

    @io.swagger.annotations.ApiModelProperty(name = "then")
    public org.apache.ambari.server.state.theme.ConfigCondition.ConfigConditionResult getThen() {
        return then;
    }

    public void setThen(org.apache.ambari.server.state.theme.ConfigCondition.ConfigConditionResult then) {
        this.then = then;
    }

    @io.swagger.annotations.ApiModelProperty(name = "else")
    public org.apache.ambari.server.state.theme.ConfigCondition.ConfigConditionResult getElseLabel() {
        return elseLabel;
    }

    public void setElseLabel(org.apache.ambari.server.state.theme.ConfigCondition.ConfigConditionResult elseLabel) {
        this.elseLabel = elseLabel;
    }

    @io.swagger.annotations.ApiModelProperty(name = "resource")
    public java.lang.String getResource() {
        return resource;
    }

    public void setResource(java.lang.String resource) {
        this.resource = resource;
    }

    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
    @com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
    public static class ConfigConditionResult implements org.apache.ambari.server.controller.ApiModel {
        @com.fasterxml.jackson.annotation.JsonProperty("property_value_attributes")
        private org.apache.ambari.server.state.ValueAttributesInfo propertyValueAttributes;

        @io.swagger.annotations.ApiModelProperty(name = "property_value_attributes")
        public org.apache.ambari.server.state.ValueAttributesInfo getPropertyValueAttributes() {
            return propertyValueAttributes;
        }

        public void setPropertyValueAttributes(org.apache.ambari.server.state.ValueAttributesInfo propertyValueAttributes) {
            this.propertyValueAttributes = propertyValueAttributes;
        }
    }
}
