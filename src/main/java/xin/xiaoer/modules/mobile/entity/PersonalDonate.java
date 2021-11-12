package xin.xiaoer.modules.mobile.entity;

import java.io.Serializable;
import java.util.Date;

public class PersonalDonate implements Serializable {
    private static final long serialVersionUID = 1L;

    /****/
    private Long itemId;
    /****/
    private Integer spaceId;
    /****/
    private String title;
    /****/
    private String description;
    /****/
    private String featuredImage;
    /****/
    private String donateAmount;
    /****/
    private Date donateDate;

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
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取：
     */
    public String getTitle() {
        return title;
    }

    public String getDonateAmount() {
        return donateAmount;
    }

    public void setDonateAmount(String donateAmount) {
        this.donateAmount = donateAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDonateDate() {
        return donateDate;
    }

    public void setDonateDate(Date donateDate) {
        this.donateDate = donateDate;
    }

    public String getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
    }
}
