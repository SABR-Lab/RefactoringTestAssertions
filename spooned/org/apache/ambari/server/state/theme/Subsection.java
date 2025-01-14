package org.apache.ambari.server.state.theme;
import io.swagger.annotations.ApiModelProperty;
@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
public class Subsection {
    @com.fasterxml.jackson.annotation.JsonProperty("row-index")
    private java.lang.String rowIndex;

    @com.fasterxml.jackson.annotation.JsonProperty("name")
    private java.lang.String name;

    @com.fasterxml.jackson.annotation.JsonProperty("display-name")
    private java.lang.String displayName;

    @com.fasterxml.jackson.annotation.JsonProperty("column-span")
    private java.lang.String columnSpan;

    @com.fasterxml.jackson.annotation.JsonProperty("row-span")
    private java.lang.String rowSpan;

    @com.fasterxml.jackson.annotation.JsonProperty("column-index")
    private java.lang.String columnIndex;

    @com.fasterxml.jackson.annotation.JsonProperty("border")
    private java.lang.String border;

    @com.fasterxml.jackson.annotation.JsonProperty("left-vertical-splitter")
    private java.lang.Boolean leftVerticalSplitter;

    @com.fasterxml.jackson.annotation.JsonProperty("depends-on")
    private java.util.List<org.apache.ambari.server.state.theme.ConfigCondition> dependsOn;

    @com.fasterxml.jackson.annotation.JsonProperty("subsection-tabs")
    private java.util.List<org.apache.ambari.server.state.theme.Subsection.SubsectionTab> subsectionTabs;

    @io.swagger.annotations.ApiModelProperty(name = "row-index")
    public java.lang.String getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(java.lang.String rowIndex) {
        this.rowIndex = rowIndex;
    }

    @io.swagger.annotations.ApiModelProperty(name = "name")
    public java.lang.String getName() {
        return name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

    @io.swagger.annotations.ApiModelProperty(name = "column-span")
    public java.lang.String getColumnSpan() {
        return columnSpan;
    }

    public void setColumnSpan(java.lang.String columnSpan) {
        this.columnSpan = columnSpan;
    }

    @io.swagger.annotations.ApiModelProperty(name = "row-span")
    public java.lang.String getRowSpan() {
        return rowSpan;
    }

    public void setRowSpan(java.lang.String rowSpan) {
        this.rowSpan = rowSpan;
    }

    @io.swagger.annotations.ApiModelProperty(name = "column-index")
    public java.lang.String getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(java.lang.String columnIndex) {
        this.columnIndex = columnIndex;
    }

    @io.swagger.annotations.ApiModelProperty(name = "display-name")
    public java.lang.String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(java.lang.String displayName) {
        this.displayName = displayName;
    }

    @io.swagger.annotations.ApiModelProperty(name = "border")
    public java.lang.String getBorder() {
        return border;
    }

    public void setBorder(java.lang.String border) {
        this.border = border;
    }

    @io.swagger.annotations.ApiModelProperty(name = "left-vertical-splitter")
    public java.lang.Boolean getLeftVerticalSplitter() {
        return leftVerticalSplitter;
    }

    public void setLeftVerticalSplitter(java.lang.Boolean leftVerticalSplitter) {
        this.leftVerticalSplitter = leftVerticalSplitter;
    }

    @io.swagger.annotations.ApiModelProperty(name = "depends-on")
    public java.util.List<org.apache.ambari.server.state.theme.ConfigCondition> getDependsOn() {
        return dependsOn;
    }

    public void setDependsOn(java.util.List<org.apache.ambari.server.state.theme.ConfigCondition> dependsOn) {
        this.dependsOn = dependsOn;
    }

    @io.swagger.annotations.ApiModelProperty(name = "subsection-tab")
    public java.util.List<org.apache.ambari.server.state.theme.Subsection.SubsectionTab> getSubsectionTabs() {
        return subsectionTabs;
    }

    public void setSubsectionTabs(java.util.List<org.apache.ambari.server.state.theme.Subsection.SubsectionTab> subsectionTabs) {
        this.subsectionTabs = subsectionTabs;
    }

    @io.swagger.annotations.ApiModelProperty(name = "removed")
    public boolean isRemoved() {
        return (((((rowIndex == null) && (rowSpan == null)) && (columnIndex == null)) && (columnSpan == null)) && (dependsOn == null)) && (subsectionTabs == null);
    }

    public void mergeWithParent(org.apache.ambari.server.state.theme.Subsection parent) {
        if (rowSpan == null) {
            rowSpan = parent.rowSpan;
        }
        if (rowIndex == null) {
            rowIndex = parent.rowIndex;
        }
        if (columnSpan == null) {
            columnSpan = parent.columnSpan;
        }
        if (columnIndex == null) {
            columnIndex = parent.columnIndex;
        }
        if (displayName == null) {
            displayName = parent.displayName;
        }
        if (border == null) {
            border = parent.border;
        }
        if (leftVerticalSplitter == null) {
            leftVerticalSplitter = parent.leftVerticalSplitter;
        }
        if (dependsOn == null) {
            dependsOn = parent.dependsOn;
        }
        if (subsectionTabs == null) {
            subsectionTabs = parent.subsectionTabs;
        }
    }

    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
    @com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
    private static class SubsectionTab {
        @com.fasterxml.jackson.annotation.JsonProperty("name")
        private java.lang.String name;

        @com.fasterxml.jackson.annotation.JsonProperty("display-name")
        private java.lang.String displayName;

        @com.fasterxml.jackson.annotation.JsonProperty("depends-on")
        private java.util.List<org.apache.ambari.server.state.theme.ConfigCondition> dependsOn;

        public java.lang.String getName() {
            return name;
        }

        public void setName(java.lang.String name) {
            this.name = name;
        }

        public java.lang.String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(java.lang.String displayName) {
            this.displayName = displayName;
        }

        public java.util.List<org.apache.ambari.server.state.theme.ConfigCondition> getDependsOn() {
            return dependsOn;
        }

        public void setDependsOn(java.util.List<org.apache.ambari.server.state.theme.ConfigCondition> dependsOn) {
            this.dependsOn = dependsOn;
        }
    }
}
