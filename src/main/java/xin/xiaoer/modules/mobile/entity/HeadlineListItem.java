package xin.xiaoer.modules.mobile.entity;

import java.io.Serializable;
import java.util.Date;

public class HeadlineListItem implements Serializable {
    private static final long serialVersionUID = 1L;
    /****/
    private Integer headlineId;
    /****/
    private String title;
    /****/
    private String content;
    /****/
    private String featuredImage;
    /****/
    private Date createAt;
    /****/
    private Long createBy;

    private String createUser;

    private boolean likeYn;

    private Integer reviewCount;

    private Integer likeCount;

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    private String authorName;
    /**
     * 设置：
     */
    public void setHeadlineId(Integer headlineId) {
        this.headlineId = headlineId;
    }

    /**
     * 获取：
     */
    public Integer getHeadlineId() {
        return headlineId;
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
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取：
     */
    public String getContent() {
        return content;
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
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /**
     * 获取：
     */
    public Date getCreateAt() {
        return createAt;
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

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public boolean getLikeYn() {
        return likeYn;
    }

    public void setLikeYn(boolean likeYn) {
        this.likeYn = likeYn;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
