package xin.xiaoer.entity;

import java.io.Serializable;

public class UserLevel implements Serializable {
    private static final long serialVersionUID = 1L;

    private String level1Min;
    private String level1Max;
    private String level2Min;
    private String level2Max;
    private String level3Min;
    private String level3Max;
    private String level4Min;
    private String level4Max;
    private String level5Min;
    private String level5Max;

    public String getLevel1Max() {
        return level1Max;
    }

    public String getLevel1Min() {
        return level1Min;
    }

    public String getLevel2Max() {
        return level2Max;
    }

    public String getLevel2Min() {
        return level2Min;
    }

    public String getLevel3Max() {
        return level3Max;
    }

    public String getLevel3Min() {
        return level3Min;
    }

    public String getLevel4Max() {
        return level4Max;
    }

    public String getLevel4Min() {
        return level4Min;
    }

    public String getLevel5Min() {
        return level5Min;
    }

    public void setLevel1Max(String level1Max) {
        this.level1Max = level1Max;
    }

    public void setLevel1Min(String level1Min) {
        this.level1Min = level1Min;
    }

    public void setLevel2Max(String level2Max) {
        this.level2Max = level2Max;
    }

    public void setLevel2Min(String level2Min) {
        this.level2Min = level2Min;
    }

    public void setLevel3Max(String level3Max) {
        this.level3Max = level3Max;
    }

    public void setLevel3Min(String level3Min) {
        this.level3Min = level3Min;
    }

    public void setLevel4Max(String level4Max) {
        this.level4Max = level4Max;
    }

    public void setLevel4Min(String level4Min) {
        this.level4Min = level4Min;
    }

    public void setLevel5Max(String level5Max) {
        this.level5Max = level5Max;
    }

    public String getLevel5Max() {
        return level5Max;
    }

    public void setLevel5Min(String level5Min) {
        this.level5Min = level5Min;
    }
}

