package xin.xiaoer.modules.mobile.entity;

import java.io.Serializable;
import java.util.Date;

public class ActivityListItem implements Serializable {
    private static final long serialVersionUID = 1L;
    /****/
    private Integer activityId;
    /****/
    private String activityTypeCode;
    /****/
    private String activityType;
    /****/
    private String title;
    /****/
    private String featuredImage;
    /****/
    private Date registerEnd;
    /****/
    private String activityStatusCode;
    /****/
    private String activityStatus;
    /****/
    private Integer numberOfPeople;
    /****/
    private Integer totalUsers;

    private boolean attendYN;


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
    public void setActivityTypeCode(String activityTypeCode) {
        this.activityTypeCode = activityTypeCode;
    }

    /**
     * 获取：
     */
    public String getActivityTypeCode() {
        return activityTypeCode;
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
    public void setRegisterEnd(Date registerEnd) {
        this.registerEnd = registerEnd;
    }

    /**
     * 获取：
     */
    public Date getRegisterEnd() {
        return registerEnd;
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

    public String getActivityType() {
        return activityStatus;
    }

    public void setActivityType(String activityStatus) {
        this.activityStatus = activityStatus;
    }

    public String getActivityStatus() {
        return activityType;
    }

    public void setActivityStatus(String activityType) {
        this.activityType = activityType;
    }

    public boolean getAttendYN() {
        return attendYN;
    }

    public void setAttendYN(boolean attendYN) {
        this.attendYN = attendYN;
    }
}
