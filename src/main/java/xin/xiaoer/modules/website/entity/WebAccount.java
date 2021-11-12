package xin.xiaoer.modules.website.entity;

import java.util.Date;

/**
 * @author XiaoDong
 */
public class WebAccount {
    private Long id;
    private Long itemId;
    private Long userId;
    private Double donateAmount;
    private String title;
    private Date createAt;
    private String anonymous;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getDonateAmount() {
        return donateAmount;
    }

    public void setDonateAmount(Double donateAmount) {
        this.donateAmount = donateAmount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(String anonymous) {
        this.anonymous = anonymous;
    }
}
