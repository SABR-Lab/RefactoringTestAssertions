package org.apache.ambari.server.state.quicklinks;
@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
public class Port {
    @com.fasterxml.jackson.annotation.JsonProperty("http_property")
    private java.lang.String httpProperty;

    @com.fasterxml.jackson.annotation.JsonProperty("http_default_port")
    private java.lang.String httpDefaultPort;

    @com.fasterxml.jackson.annotation.JsonProperty("https_property")
    private java.lang.String httpsProperty;

    @com.fasterxml.jackson.annotation.JsonProperty("https_default_port")
    private java.lang.String httpsDefaultPort;

    @com.fasterxml.jackson.annotation.JsonProperty("regex")
    private java.lang.String regex;

    @com.fasterxml.jackson.annotation.JsonProperty("https_regex")
    private java.lang.String httpsRegex;

    @com.fasterxml.jackson.annotation.JsonProperty("site")
    private java.lang.String site;

    public java.lang.String getHttpProperty() {
        return httpProperty;
    }

    public void setHttpProperty(java.lang.String httpProperty) {
        this.httpProperty = httpProperty;
    }

    public java.lang.String getHttpDefaultPort() {
        return httpDefaultPort;
    }

    public void setHttpDefaultPort(java.lang.String httpDefaultPort) {
        this.httpDefaultPort = httpDefaultPort;
    }

    public java.lang.String getHttpsProperty() {
        return httpsProperty;
    }

    public void setHttpsProperty(java.lang.String httpsProperty) {
        this.httpsProperty = httpsProperty;
    }

    public java.lang.String getHttpsDefaultPort() {
        return httpsDefaultPort;
    }

    public void setHttpsDefaultPort(java.lang.String httpsDefaultPort) {
        this.httpsDefaultPort = httpsDefaultPort;
    }

    public java.lang.String getRegex() {
        return regex;
    }

    public void setRegex(java.lang.String regex) {
        this.regex = regex;
    }

    public java.lang.String getHttpsRegex() {
        return httpsRegex;
    }

    public void setHttpsRegex(java.lang.String httpsRegex) {
        this.httpsRegex = httpsRegex;
    }

    public java.lang.String getSite() {
        return site;
    }

    public void setSite(java.lang.String site) {
        this.site = site;
    }

    public void mergetWithParent(org.apache.ambari.server.state.quicklinks.Port parentPort) {
        if (null == parentPort)
            return;

        if ((null == httpProperty) && (null != parentPort.getHttpProperty()))
            httpProperty = parentPort.getHttpProperty();

        if ((null == httpDefaultPort) && (null != parentPort.getHttpDefaultPort()))
            httpDefaultPort = parentPort.getHttpDefaultPort();

        if ((null == httpsProperty) && (null != parentPort.getHttpsProperty()))
            httpsProperty = parentPort.getHttpsProperty();

        if ((null == httpsDefaultPort) && (null != parentPort.getHttpsDefaultPort()))
            httpsDefaultPort = parentPort.getHttpsDefaultPort();

        if ((null == regex) && (null != parentPort.getRegex()))
            regex = parentPort.getRegex();

        if ((null == httpsRegex) && (null != parentPort.getHttpsRegex()))
            regex = parentPort.getHttpsRegex();

        if ((null == site) && (null != parentPort.getSite()))
            site = parentPort.getSite();

    }
}
