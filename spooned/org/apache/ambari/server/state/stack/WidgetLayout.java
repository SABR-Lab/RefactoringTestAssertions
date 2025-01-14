package org.apache.ambari.server.state.stack;
public class WidgetLayout {
    @com.google.gson.annotations.SerializedName("layout_name")
    private java.lang.String layoutName;

    @com.google.gson.annotations.SerializedName("section_name")
    private java.lang.String sectionName;

    @com.google.gson.annotations.SerializedName("display_name")
    private java.lang.String displayName;

    @com.google.gson.annotations.SerializedName("widgetLayoutInfo")
    private java.util.List<org.apache.ambari.server.state.stack.WidgetLayoutInfo> widgetLayoutInfoList;

    @com.fasterxml.jackson.annotation.JsonProperty("layout_name")
    public java.lang.String getLayoutName() {
        return layoutName;
    }

    public void setLayoutName(java.lang.String layoutName) {
        this.layoutName = layoutName;
    }

    public java.lang.String getSectionName() {
        return sectionName;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("section_name")
    public void setSectionName(java.lang.String sectionName) {
        this.sectionName = sectionName;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("display_name")
    public java.lang.String getDisplayName() {
        return displayName;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("widgetLayoutInfo")
    public java.util.List<org.apache.ambari.server.state.stack.WidgetLayoutInfo> getWidgetLayoutInfoList() {
        return widgetLayoutInfoList;
    }

    public void setWidgetLayoutInfoList(java.util.List<org.apache.ambari.server.state.stack.WidgetLayoutInfo> widgetLayoutInfoList) {
        this.widgetLayoutInfoList = widgetLayoutInfoList;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return (((((((("WidgetLayout{" + "layoutName='") + layoutName) + '\'') + ", sectionName='") + sectionName) + '\'') + ", widgetLayoutInfoList=") + widgetLayoutInfoList) + '}';
    }
}
