package xin.xiaoer.modules.activityappraisal.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-07-25 01:47:05
 */
public class ActivityAppraisal implements Serializable {
    private static final long serialVersionUID = 1L;

    /****/
    private Integer appraisalId;
    /****/
    private Integer spaceId;
    /****/
    private String spaceName;
    /****/
    private String title;
    /****/
    private Integer htmlId;
    /****/
    private String featuredImage;
    /****/
    private String contentFile;
    /****/
    private Integer readCount;
    /****/
    private String state;
    /****/
    private Date createAt;
    /****/
    private Long createBy;
    /****/
    private Date updateAt;
    /****/
    private Long updateBy;
    /**
     * 设置：
     */
    public void setAppraisalId(Integer appraisalId) {
        this.appraisalId = appraisalId;
    }

    /**
     * 获取：
     */
    public Integer getAppraisalId() {
        return appraisalId;
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

    public String getSpaceName() {
        return spaceName;
    }

    public void setSpaceName(String spaceName) {
        this.spaceName = spaceName;
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
    public void setContentFile(String contentFile) {
        this.contentFile = contentFile;
    }

    /**
     * 获取：
     */
    public String getContentFile() {
        return contentFile;
    }

    /**
     * 设置：
     */
    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    /**
     * 获取：
     */
    public Integer getReadCount() {
        return readCount;
    }

    /**
     * 设置：
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 获取：
     */
    public String getState() {
        return state;
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

    /**
     * 设置：
     */
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    /**
     * 获取：
     */
    public Date getUpdateAt() {
        return updateAt;
    }

    /**
     * 设置：
     */
    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 获取：
     */
    public Long getUpdateBy() {
        return updateBy;
    }

    public Integer getHtmlId() {
        return htmlId;
    }

    public void setHtmlId(Integer htmlId) {
        this.htmlId = htmlId;
    }
}
