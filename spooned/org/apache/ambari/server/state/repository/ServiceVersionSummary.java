package org.apache.ambari.server.state.repository;
public class ServiceVersionSummary {
    @com.google.gson.annotations.SerializedName("version")
    @com.fasterxml.jackson.annotation.JsonProperty("version")
    private java.lang.String m_version;

    @com.google.gson.annotations.SerializedName("release_version")
    @com.fasterxml.jackson.annotation.JsonProperty("release_version")
    private java.lang.String m_releaseVersion;

    @com.google.gson.annotations.SerializedName("upgrade")
    @com.fasterxml.jackson.annotation.JsonProperty("upgrade")
    private boolean m_upgrade = false;

    ServiceVersionSummary() {
    }

    void setVersions(java.lang.String binaryVersion, java.lang.String releaseVersion) {
        m_version = binaryVersion;
        m_releaseVersion = releaseVersion;
    }

    @com.fasterxml.jackson.annotation.JsonIgnore
    public boolean isUpgrade() {
        return m_upgrade;
    }

    public void setUpgrade(boolean upgrade) {
        m_upgrade = upgrade;
    }

    @com.fasterxml.jackson.annotation.JsonIgnore
    public java.lang.String getReleaseVersion() {
        return m_releaseVersion;
    }
}
