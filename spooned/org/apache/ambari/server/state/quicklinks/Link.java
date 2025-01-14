package org.apache.ambari.server.state.quicklinks;
import javax.annotation.Nullable;
@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
public class Link {
    @com.fasterxml.jackson.annotation.JsonProperty("name")
    private java.lang.String name;

    @com.fasterxml.jackson.annotation.JsonProperty("label")
    private java.lang.String label;

    @com.fasterxml.jackson.annotation.JsonProperty("component_name")
    private java.lang.String componentName;

    @com.fasterxml.jackson.annotation.JsonProperty("requires_user_name")
    private java.lang.String requiresUserName;

    @com.fasterxml.jackson.annotation.JsonProperty("url")
    private java.lang.String url;

    @com.fasterxml.jackson.annotation.JsonProperty("port")
    private org.apache.ambari.server.state.quicklinks.Port port;

    @com.fasterxml.jackson.annotation.JsonProperty("host")
    private org.apache.ambari.server.state.quicklinks.Host host;

    @com.fasterxml.jackson.annotation.JsonProperty("protocol")
    private org.apache.ambari.server.state.quicklinks.Protocol protocol;

    @com.fasterxml.jackson.annotation.JsonProperty("attributes")
    private java.util.List<java.lang.String> attributes;

    @com.fasterxml.jackson.annotation.JsonProperty("visible")
    private boolean visible = true;

    public java.lang.String getName() {
        return name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

    public java.lang.String getLabel() {
        return label;
    }

    public void setLabel(java.lang.String label) {
        this.label = label;
    }

    public java.lang.String getComponentName() {
        return componentName;
    }

    public void setComponentName(java.lang.String componentName) {
        this.componentName = componentName;
    }

    public java.lang.String getUrl() {
        return url;
    }

    public void setUrl(java.lang.String url) {
        this.url = url;
    }

    public java.lang.String getRequiresUserName() {
        return requiresUserName;
    }

    public void setRequiresUserName(java.lang.String requiresUserName) {
        this.requiresUserName = requiresUserName;
    }

    public org.apache.ambari.server.state.quicklinks.Port getPort() {
        return port;
    }

    public org.apache.ambari.server.state.quicklinks.Host getHost() {
        return host;
    }

    public void setPort(org.apache.ambari.server.state.quicklinks.Port port) {
        this.port = port;
    }

    public org.apache.ambari.server.state.quicklinks.Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(org.apache.ambari.server.state.quicklinks.Protocol protocol) {
        this.protocol = protocol;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @javax.annotation.Nullable
    public java.util.List<java.lang.String> getAttributes() {
        return attributes;
    }

    public void setAttributes(java.util.List<java.lang.String> attributes) {
        this.attributes = attributes;
    }

    public boolean isRemoved() {
        return (((null == port) && (null == url)) && (null == label)) && (null == requiresUserName);
    }

    public void mergeWithParent(org.apache.ambari.server.state.quicklinks.Link parentLink) {
        if (null == parentLink)
            return;

        if ((null == label) && (null != parentLink.getLabel()))
            label = parentLink.getLabel();

        if ((null == componentName) && (null != parentLink.getComponentName()))
            componentName = parentLink.getComponentName();

        if ((null == url) && (null != parentLink.getUrl()))
            url = parentLink.getUrl();

        if ((null == requiresUserName) && (null != parentLink.getRequiresUserName()))
            requiresUserName = parentLink.getRequiresUserName();

        if (null == port) {
            port = parentLink.getPort();
        } else {
            port.mergetWithParent(parentLink.getPort());
        }
        if (null == host) {
            host = parentLink.getHost();
        } else {
            host.mergeWithParent(parentLink.getHost());
        }
        if ((null == attributes) && (null != parentLink.attributes)) {
            attributes = parentLink.attributes;
        }
    }
}
