package xin.xiaoer.modules.mobile.entity;

import java.io.Serializable;
import java.util.Date;

public class StudyRoomListItem implements Serializable {
    private static final long serialVersionUID = 1L;
    /****/
    private Integer roomId;
    /****/
    private Integer spaceId;
    /****/
    private String srTypeCode;
    /****/
    private String title;
    /****/
    private String featuredImage;
    /****/
    private Integer numberOfPeople;
    /****/
    private Integer totalAttends;
    /****/
    private String srStatusCode;
    /****/
    private String srStatusName;
    //新加
    private Date createAt;
    /**
     * 设置：
     */
    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }
    /**
     * 获取：
     */
    public Integer getRoomId() {
        return roomId;
    }
    /**
     * 设置：
     */
    public void setSpaceId(Integer spaceId) {
        this.spaceId = spaceId;
    }
    /**
     * 获取：
     */
    public Integer getSpaceId() {
        return spaceId;
    }
    /**
     * 设置：
     */
    public void setSrTypeCode(String srTypeCode) {
        this.srTypeCode = srTypeCode;
    }
    /**
     * 获取：
     */
    public String getSrTypeCode() {
        return srTypeCode;
    }
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
    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
    }
    /**
     * 获取：
     */
    public String getFeaturedImage() {
        return featuredImage;
    }
    /**
     * 设置：
     */
    public void setNumberOfPeople(Integer numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }
    /**
     * 获取：
     */
    public Integer getNumberOfPeople() {
        return numberOfPeople;
    }

    public Integer getTotalAttends() {
        return totalAttends;
    }

    public void setTotalAttends(Integer totalAttends) {
        this.totalAttends = totalAttends;
    }
    /**
     * 设置：
     */
    public void setSrStatusCode(String srStatusCode) {
        this.srStatusCode = srStatusCode;
    }
    /**
     * 获取：
     */
    public String getSrStatusCode() {
        return srStatusCode;
    }

    public String getSrStatusName() {
        return srStatusName;
    }

    public void setSrStatusName(String srStatusName) {
        this.srStatusName = srStatusName;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}

