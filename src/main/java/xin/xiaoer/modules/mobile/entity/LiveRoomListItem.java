package xin.xiaoer.modules.mobile.entity;

import java.io.Serializable;
import java.util.Date;

public class LiveRoomListItem implements Serializable {
    private static final long serialVersionUID = 1L;
    /****/
    private Integer liveId;
    /****/
    private String title;
    /****/
    private String roomId;
    /****/
    private String featuredImage;
    /****/
    private String createUser;
    /****/
    private String avatar;
    /****/
    private Long createBy;
    //新加
    private Integer onlineCount;

    private boolean followYn;

    private Date lastLiveAt;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Integer getLiveId() {
        return liveId;
    }

    public void setLiveId(Integer liveId) {
        this.liveId = liveId;
    }

    public String getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public boolean getFollowYn() {
        return followYn;
    }

    public void setFollowYn(boolean followYn) {
        this.followYn = followYn;
    }

    public Integer getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(Integer onlineCount) {
        this.onlineCount = onlineCount;
    }

    public Date getLastLiveAt() {
        return lastLiveAt;
    }

    public void setLastLiveAt(Date lastLiveAt) {
        this.lastLiveAt = lastLiveAt;
    }
}

