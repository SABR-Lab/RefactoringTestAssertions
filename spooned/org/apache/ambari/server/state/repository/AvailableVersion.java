package org.apache.ambari.server.state.repository;
public class AvailableVersion {
    @com.fasterxml.jackson.annotation.JsonProperty("version")
    private java.lang.String version;

    @com.fasterxml.jackson.annotation.JsonProperty("release_version")
    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
    private java.lang.String releaseVersion;

    @com.fasterxml.jackson.annotation.JsonProperty("version_id")
    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
    private java.lang.String versionId;

    @com.fasterxml.jackson.annotation.JsonProperty
    private java.util.Set<org.apache.ambari.server.state.repository.AvailableVersion.Component> components;

    AvailableVersion(java.lang.String version, java.lang.String versionId, java.lang.String releaseVersion, java.util.Set<org.apache.ambari.server.state.repository.AvailableVersion.Component> components) {
        this.version = version;
        this.versionId = versionId;
        this.releaseVersion = releaseVersion;
        this.components = components;
    }

    public java.lang.String getVersion() {
        return version;
    }

    public java.lang.String getReleaseVersion() {
        return releaseVersion;
    }

    static class Component {
        @com.fasterxml.jackson.annotation.JsonProperty("name")
        private java.lang.String name;

        @com.fasterxml.jackson.annotation.JsonProperty("display_name")
        private java.lang.String display;

        Component(java.lang.String name, java.lang.String display) {
            this.name = name;
            this.display = display;
        }
    }
}
