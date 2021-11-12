package xin.xiaoer.modules.mobile.entity;

import java.io.Serializable;

public class DonateUserResume implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long itemId;
    /****/
    private Double donateAmount;
    /****/
    private Integer donateUserCount;


    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getItemId() {
        return itemId;
    }

    public Integer getDonateUserCount() {
        return donateUserCount;
    }

    public void setDonateUserCount(Integer donateUserCount) {
        this.donateUserCount = donateUserCount;
    }

    public Double getDonateAmount() {
        return donateAmount;
    }

    public void setDonateAmount(Double donateAmount) {
        this.donateAmount = donateAmount;
    }
}
