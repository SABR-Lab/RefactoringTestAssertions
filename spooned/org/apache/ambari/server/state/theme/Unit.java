package org.apache.ambari.server.state.theme;
import io.swagger.annotations.ApiModelProperty;
@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
public class Unit implements org.apache.ambari.server.controller.ApiModel {
    @com.fasterxml.jackson.annotation.JsonProperty("unit-name")
    private java.lang.String unitName;

    @io.swagger.annotations.ApiModelProperty(name = "unit-name")
    public java.lang.String getUnitName() {
        return unitName;
    }

    public void setUnitName(java.lang.String unitName) {
        this.unitName = unitName;
    }
}
