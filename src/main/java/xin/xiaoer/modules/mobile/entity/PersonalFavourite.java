package xin.xiaoer.modules.mobile.entity;

import java.io.Serializable;
import java.util.Date;

public class PersonalFavourite implements Serializable {
    private static final long serialVersionUID = 1L;
    /****/
    private Long articleId;
    /****/
    private String articleTypeCode;
    /****/
    private String title;
    /****/
    private String content;
    /****/
    private String featuredImage;
    /****/
    private Date createAt;
    /**
     * 设置：
     */
    public Long getArticleId() {
        return articleId;
    }

    /**
     * 获取：
     */
    public void setArticleId(Long articleId) {
        this.articleId = articleId;
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

    public String getArticleTypeCode() {
        return articleTypeCode;
    }

    public void setArticleTypeCode(String articleTypeCode) {
        this.articleTypeCode = articleTypeCode;
    }
}
