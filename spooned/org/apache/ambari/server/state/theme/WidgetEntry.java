package org.apache.ambari.server.state.theme;
import io.swagger.annotations.ApiModelProperty;
@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
public class WidgetEntry implements org.apache.ambari.server.controller.ApiModel {
    @com.fasterxml.jackson.annotation.JsonProperty("config")
    private java.lang.String config;

    @com.fasterxml.jackson.annotation.JsonProperty("widget")
    private org.apache.ambari.server.state.theme.Widget widget;

    @io.swagger.annotations.ApiModelProperty(name = "config")
    public java.lang.String getConfig() {
        return config;
    }

    public void setConfig(java.lang.String config) {
        this.config = config;
    }

    @io.swagger.annotations.ApiModelProperty(name = "widget")
    public org.apache.ambari.server.state.theme.Widget getWidget() {
        return widget;
    }

    public void setWidget(org.apache.ambari.server.state.theme.Widget widget) {
        this.widget = widget;
    }
}
