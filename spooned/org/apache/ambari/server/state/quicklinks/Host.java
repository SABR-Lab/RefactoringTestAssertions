package org.apache.ambari.server.state.quicklinks;
@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
public class Host {
    @com.fasterxml.jackson.annotation.JsonProperty("http_property")
    private java.lang.String httpProperty;

    @com.fasterxml.jackson.annotation.JsonProperty("https_property")
    private java.lang.String httpsProperty;

    @com.fasterxml.jackson.annotation.JsonProperty("site")
    private java.lang.String site;

    public java.lang.String getHttpProperty() {
        return httpProperty;
    }

    public java.lang.String getHttpsProperty() {
        return httpsProperty;
    }

    public java.lang.String getSite() {
        return site;
    }

    public void mergeWithParent(org.apache.ambari.server.state.quicklinks.Host parentHost) {
        if (null == parentHost) {
            return;
        }
        if ((null == httpProperty) && (null != parentHost.getHttpProperty())) {
            httpProperty = parentHost.getHttpProperty();
        }
        if ((null == httpsProperty) && (null != parentHost.getHttpsProperty())) {
            httpsProperty = parentHost.getHttpsProperty();
        }
        if ((null == site) && (null != parentHost.getSite())) {
            site = parentHost.getSite();
        }
    }
}
