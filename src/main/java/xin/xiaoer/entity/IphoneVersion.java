package xin.xiaoer.entity;

import java.io.Serializable;

public class IphoneVersion implements Serializable {
    private static final long serialVersionUID = 1L;

    private String bundleShortVersionString;
    private String bundleVersion;
    private String bundleIdentifier;

    public String getBundleShortVersionString() {
        return bundleShortVersionString;
    }

    public void setBundleShortVersionString(String bundleShortVersionString) {
        this.bundleShortVersionString = bundleShortVersionString;
    }

    public String getBundleVersion() {
        return bundleVersion;
    }

    public void setBundleVersion(String bundleVersion) {
        this.bundleVersion = bundleVersion;
    }

    public String getBundleIdentifier() {
        return bundleIdentifier;
    }

    public void setBundleIdentifier(String bundleIdentifier) {
        this.bundleIdentifier = bundleIdentifier;
    }
}

