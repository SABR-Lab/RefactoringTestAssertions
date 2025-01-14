package org.apache.ambari.server.state.repository;
public class ManifestServiceInfo {
    @com.fasterxml.jackson.annotation.JsonProperty("name")
    java.lang.String m_name;

    @com.fasterxml.jackson.annotation.JsonProperty("display_name")
    java.lang.String m_display;

    @com.fasterxml.jackson.annotation.JsonProperty("comment")
    java.lang.String m_comment;

    @com.fasterxml.jackson.annotation.JsonProperty("versions")
    java.util.Set<java.lang.String> m_versions;

    public ManifestServiceInfo(java.lang.String name, java.lang.String display, java.lang.String comment, java.util.Set<java.lang.String> versions) {
        m_name = name;
        m_display = display;
        m_comment = comment;
        m_versions = versions;
    }

    public void setM_name(java.lang.String m_name) {
        this.m_name = m_name;
    }

    public java.lang.String getM_name() {
        return m_name;
    }

    public void setM_display(java.lang.String m_display) {
        this.m_display = m_display;
    }

    public java.lang.String getM_display() {
        return m_display;
    }

    public void setM_comment(java.lang.String m_comment) {
        this.m_comment = m_comment;
    }

    public java.lang.String getM_comment() {
        return m_comment;
    }

    public void setM_versions(java.util.Set<java.lang.String> m_versions) {
        this.m_versions = m_versions;
    }

    public java.util.Set<java.lang.String> getM_versions() {
        return m_versions;
    }
}
