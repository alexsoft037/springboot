package xin.xiaoer.modules.setting.entity;

public class XiaoerMessage {
    private static final long serialVersionUID = 1L;

    /****/
    private String title;
    /****/
    private String content;

    private String deviceId;
    /**
     * 设置：
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * 获取：
     */
    public String getTitle() {
        return title;
    }
    /**
     * 设置：
     */
    public void setContent(String content) {
        this.content = content;
    }
    /**
     * 获取：
     */
    public String getContent() {
        return content;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}

