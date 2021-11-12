package xin.xiaoer.modules.mobile.entity;

import java.io.Serializable;
import java.util.Date;

public class PersonalActivity implements Serializable {
    private static final long serialVersionUID = 1L;
    /****/
    private Integer activityId;
    /****/
    private String title;
    /****/
    private String activityTypeCode;
    /****/
    private String featuredImage;
    /****/
    private String activityStatusCode;
    /****/
    private Integer numberOfPeople;
    /****/
    private Integer totalUsers;
    /****/
    private Date periodFrom;
    /****/
    private Date periodTo;
    /****/
    private Integer integral;
    /**
     * 设置：
     */
    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    /**
     * 获取：
     */
    public Integer getActivityId() {
        return activityId;
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
    public void setActivityStatusCode(String activityStatusCode) {
        this.activityStatusCode = activityStatusCode;
    }

    /**
     * 获取：
     */
    public String getActivityStatusCode() {
        return activityStatusCode;
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

    public Integer getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(Integer totalUsers) {
        this.totalUsers = totalUsers;
    }

    public Date getPeriodFrom() {
        return periodFrom;
    }

    public void setPeriodFrom(Date periodFrom) {
        this.periodFrom = periodFrom;
    }

    public Date getPeriodTo() {
        return periodTo;
    }

    public void setPeriodTo(Date periodTo) {
        this.periodTo = periodTo;
    }

    public String getActivityTypeCode() {
        return activityTypeCode;
    }

    public void setActivityTypeCode(String activityTypeCode) {
        this.activityTypeCode = activityTypeCode;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }
}
