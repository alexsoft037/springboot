package xin.xiaoer.modules.mobile.entity;

import java.io.Serializable;
import java.util.Date;

public class VideoLessonListItem implements Serializable {
    private static final long serialVersionUID = 1L;

    /****/
    private Integer lessonId;
    /**课程名称**/
    private String title;
    /**课程简介**/
    private String categoryCode;
    /****/
    private String featuredImage;
    /**加权因子**/
    private Integer weight;
    /****/
    private String featuredYn;
    /****/
    private Integer playCount;
    /****/
    private Long createBy;

    private String createUser;

    private String avatar;
    //新加
    private Integer readCount;
    /**
     * 设置：
     */
    public void setLessonId(Integer lessonId) {
        this.lessonId = lessonId;
    }
    /**
     * 获取：
     */
    public Integer getLessonId() {
        return lessonId;
    }
    /**
     * 设置：课程名称
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * 获取：课程名称
     */
    public String getTitle() {
        return title;
    }
    /**
     * 设置：课程类型
     */
    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }
    /**
     * 获取：课程类型
     */
    public String getCategoryCode() {
        return categoryCode;
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
     * 设置：加权因子
     */
    public void setWeight(Integer weight) {
        this.weight = weight;
    }
    /**
     * 获取：加权因子
     */
    public Integer getWeight() {
        return weight;
    }
    /**
     * 设置：
     */
    public void setFeaturedYn(String featuredYn) {
        this.featuredYn = featuredYn;
    }
    /**
     * 获取：
     */
    public String getFeaturedYn() {
        return featuredYn;
    }

    public Integer getPlayCount() {
        if (playCount == null || playCount < 0){
            playCount = 0;
            return 0;
        }
        return playCount;
    }

    public void setPlayCount(Integer playCount) {
        this.playCount = playCount;
    }

    /**
     * 设置：
     */
    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }
    /**
     * 获取：
     */
    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }
}
