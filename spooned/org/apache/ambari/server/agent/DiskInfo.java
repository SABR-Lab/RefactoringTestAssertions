package org.apache.ambari.server.agent;
public class DiskInfo {
    java.lang.String available;

    java.lang.String mountpoint;

    java.lang.String device;

    java.lang.String used;

    java.lang.String percent;

    java.lang.String size;

    java.lang.String type;

    public DiskInfo(java.lang.String device, java.lang.String mountpoint, java.lang.String available, java.lang.String used, java.lang.String percent, java.lang.String size, java.lang.String type) {
        this.device = device;
        this.mountpoint = mountpoint;
        this.available = available;
        this.used = used;
        this.percent = percent;
        this.size = size;
        this.type = type;
    }

    public DiskInfo() {
    }

    @com.fasterxml.jackson.annotation.JsonProperty("available")
    public void setAvailable(java.lang.String available) {
        this.available = available;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("available")
    public java.lang.String getAvailable() {
        return this.available;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("mountpoint")
    public java.lang.String getMountPoint() {
        return this.mountpoint;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("mountpoint")
    public void setMountPoint(java.lang.String mountpoint) {
        this.mountpoint = mountpoint;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("type")
    public java.lang.String getType() {
        return this.type;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("type")
    public void setType(java.lang.String type) {
        this.type = type;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("used")
    public java.lang.String getUsed() {
        return this.used;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("used")
    public void setUsed(java.lang.String used) {
        this.used = used;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("percent")
    public java.lang.String getPercent() {
        return this.percent;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("percent")
    public void setPercent(java.lang.String percent) {
        this.percent = percent;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("size")
    public java.lang.String getSize() {
        return this.size;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("size")
    public void setSize(java.lang.String size) {
        this.size = size;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("device")
    public java.lang.String getDevice() {
        return device;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("device")
    public void setDevice(java.lang.String device) {
        this.device = device;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return (((((((((((("available=" + this.available) + ",mountpoint=") + this.mountpoint) + ",used=") + this.used) + ",percent=") + this.percent) + ",size=") + this.size) + ",device=") + this.device) + ",type=") + this.type;
    }
}
