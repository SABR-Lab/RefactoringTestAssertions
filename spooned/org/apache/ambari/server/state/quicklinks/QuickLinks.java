package org.apache.ambari.server.state.quicklinks;
@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
public class QuickLinks {
    @com.fasterxml.jackson.annotation.JsonProperty("description")
    private java.lang.String description;

    @com.fasterxml.jackson.annotation.JsonProperty("name")
    private java.lang.String name;

    @com.fasterxml.jackson.annotation.JsonProperty("configuration")
    private org.apache.ambari.server.state.quicklinks.QuickLinksConfiguration quickLinksConfiguration;

    public java.lang.String getDescription() {
        return description;
    }

    public void setDescription(java.lang.String description) {
        this.description = description;
    }

    public java.lang.String getName() {
        return name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

    public org.apache.ambari.server.state.quicklinks.QuickLinksConfiguration getQuickLinksConfiguration() {
        return quickLinksConfiguration;
    }

    public void setQuickLinksConfiguration(org.apache.ambari.server.state.quicklinks.QuickLinksConfiguration quickLinksConfiguration) {
        this.quickLinksConfiguration = quickLinksConfiguration;
    }

    public void mergeWithParent(org.apache.ambari.server.state.quicklinks.QuickLinks parent) {
        if (parent == null) {
            return;
        }
        if (name == null) {
            name = parent.name;
        }
        if (description == null) {
            description = parent.description;
        }
        if (quickLinksConfiguration == null) {
            quickLinksConfiguration = parent.quickLinksConfiguration;
        } else {
            quickLinksConfiguration.mergeWithParent(parent.quickLinksConfiguration);
        }
    }
}
