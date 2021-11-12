package xin.xiaoer.modules.mobile.entity;

import java.io.Serializable;

public class TotalDonateAmountResume implements Serializable {
    private static final long serialVersionUID = 1L;

    private Double totalAmount;

    private Integer totalUserCount;

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getTotalUserCount() {
        return totalUserCount;
    }

    public void setTotalUserCount(Integer totalUserCount) {
        this.totalUserCount = totalUserCount;
    }
}
