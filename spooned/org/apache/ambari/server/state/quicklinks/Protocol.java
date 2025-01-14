package org.apache.ambari.server.state.quicklinks;
@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
public class Protocol {
    @com.fasterxml.jackson.annotation.JsonProperty("type")
    private java.lang.String type;

    @com.fasterxml.jackson.annotation.JsonProperty("checks")
    private java.util.List<org.apache.ambari.server.state.quicklinks.Check> checks;

    public java.lang.String getType() {
        return type;
    }

    public void setType(java.lang.String type) {
        this.type = type;
    }

    public java.util.List<org.apache.ambari.server.state.quicklinks.Check> getChecks() {
        return checks;
    }

    public void setChecks(java.util.List<org.apache.ambari.server.state.quicklinks.Check> checks) {
        this.checks = checks;
    }
}
