package xin.xiaoer.modules.mobile.entity;

import java.io.Serializable;
import java.util.Date;

public class DSpaceListItem implements Serializable {
    private static final long serialVersionUID = 1L;

    /****/
    private Long itemId;
    /****/
    private Integer spaceId;
    /****/
    private String type;
    /****/
    private String typeCode;
    /****/
    private String title;
    /****/
    private String feturedImage;
    /****/
    private String status;
    /****/
    private String statusCode;
    /****/
    private Integer donateUserCount;
    /****/
    private Date createAt;

    /**
     * 设置：
     */
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    /**
     * 获取：
     */
    public Long getItemId() {
        return itemId;
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
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取：
     */
    public String getType() {
        return type;
    }
    /**
     * 设置：
     */
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    /**
     * 获取：
     */
    public String getTypeCode() {
        return typeCode;
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
    public void setFeturedImage(String feturedImage) {
        this.feturedImage = feturedImage;
    }

    /**
     * 获取：
     */
    public String getFeturedImage() {
        return feturedImage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public Integer getDonateUserCount() {
        return donateUserCount;
    }

    public void setDonateUserCount(Integer donateUserCount) {
        this.donateUserCount = donateUserCount;
    }
    /**
     * 设置：
     */
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /**
     * 获取：
     */
    public Date getCreateAt() {
        return createAt;
    }
}
