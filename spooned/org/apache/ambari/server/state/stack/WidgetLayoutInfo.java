package org.apache.ambari.server.state.stack;
public class WidgetLayoutInfo {
    @com.google.gson.annotations.SerializedName("widget_name")
    private java.lang.String widgetName;

    @com.google.gson.annotations.SerializedName("default_section_name")
    private java.lang.String defaultSectionName = null;

    @com.google.gson.annotations.SerializedName("description")
    private java.lang.String description;

    @com.google.gson.annotations.SerializedName("widget_type")
    private java.lang.String type;

    @com.google.gson.annotations.SerializedName("is_visible")
    private boolean visibility;

    @com.google.gson.annotations.SerializedName("metrics")
    private java.util.List<java.util.Map<java.lang.String, java.lang.String>> metricsInfo;

    @com.google.gson.annotations.SerializedName("values")
    private java.util.List<java.util.Map<java.lang.String, java.lang.String>> values;

    @com.google.gson.annotations.SerializedName("properties")
    private java.util.Map<java.lang.String, java.lang.String> properties;

    @com.fasterxml.jackson.annotation.JsonProperty("widget_name")
    public java.lang.String getWidgetName() {
        return widgetName;
    }

    public void setWidgetName(java.lang.String widgetName) {
        this.widgetName = widgetName;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("default_section_name")
    public java.lang.String getDefaultSectionName() {
        return defaultSectionName;
    }

    public void setDefaultSectionName(java.lang.String defaultSectionName) {
        this.defaultSectionName = defaultSectionName;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("description")
    public java.lang.String getDescription() {
        return description;
    }

    public void setDescription(java.lang.String description) {
        this.description = description;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("widget_type")
    public java.lang.String getType() {
        return type;
    }

    public void setType(java.lang.String type) {
        this.type = type;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("is_visible")
    public boolean isVisible() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("metrics")
    public java.util.List<java.util.Map<java.lang.String, java.lang.String>> getMetricsInfo() {
        return metricsInfo;
    }

    public void setMetricsInfo(java.util.List<java.util.Map<java.lang.String, java.lang.String>> metricsInfo) {
        this.metricsInfo = metricsInfo;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("values")
    public java.util.List<java.util.Map<java.lang.String, java.lang.String>> getValues() {
        return values;
    }

    public void setValues(java.util.List<java.util.Map<java.lang.String, java.lang.String>> values) {
        this.values = values;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("properties")
    public java.util.Map<java.lang.String, java.lang.String> getProperties() {
        return properties;
    }

    public void setProperties(java.util.Map<java.lang.String, java.lang.String> properties) {
        this.properties = properties;
    }
}
