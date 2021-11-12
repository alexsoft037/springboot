package xin.xiaoer.modules.website.entity;

import java.util.Date;

/**
 * @ClassName VideoBarrage
 * @Description TODO 视频弹幕
 * @Author XiaoDong
 **/
public class VideoBarrage {
    private Integer id;
    private Long userId;
    private Integer videoId;
    private Double barrageTime;
    private Integer barrageState;
    private Integer barrageId;
    private String barrageStyle;
    private String barrageContent;
    private Date createAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public Double getBarrageTime() {
        return barrageTime;
    }

    public void setBarrageTime(Double barrageTime) {
        this.barrageTime = barrageTime;
    }

    public Integer getBarrageState() {
        return barrageState;
    }

    public void setBarrageState(Integer barrageState) {
        this.barrageState = barrageState;
    }

    public Integer getBarrageId() {
        return barrageId;
    }

    public void setBarrageId(Integer barrageId) {
        this.barrageId = barrageId;
    }

    public String getBarrageStyle() {
        return barrageStyle;
    }

    public void setBarrageStyle(String barrageStyle) {
        this.barrageStyle = barrageStyle;
    }

    public String getBarrageContent() {
        return barrageContent;
    }

    public void setBarrageContent(String barrageContent) {
        this.barrageContent = barrageContent;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
