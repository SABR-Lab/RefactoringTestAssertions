package org.apache.ambari.server.state.quicklinks;
@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
public class Check {
    @com.fasterxml.jackson.annotation.JsonProperty("property")
    private java.lang.String property;

    @com.fasterxml.jackson.annotation.JsonProperty("desired")
    private java.lang.String desired;

    @com.fasterxml.jackson.annotation.JsonProperty("site")
    private java.lang.String site;

    public java.lang.String getProperty() {
        return property;
    }

    public void setProperty(java.lang.String property) {
        this.property = property;
    }

    public java.lang.String getDesired() {
        return desired;
    }

    public void setDesired(java.lang.String desired) {
        this.desired = desired;
    }

    public java.lang.String getSite() {
        return site;
    }

    public void setSite(java.lang.String site) {
        this.site = site;
    }
}
