package xin.xiaoer.entity;

import java.io.Serializable;

public class AndroidVersion implements Serializable {
    private static final long serialVersionUID = 1L;

    private String versionName;
    private String versionCode;
    private String applicationId;

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }
}

