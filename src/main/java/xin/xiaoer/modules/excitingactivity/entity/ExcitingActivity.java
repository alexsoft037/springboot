package xin.xiaoer.modules.excitingactivity.entity;

import java.io.Serializable;
import java.util.Date;

public class ExcitingActivity implements Serializable{
    private static final long serialVersionUID = 1L;
    /****/
    private Integer id;
    /****/
    private String title;
    /****/
    private Integer spaceId;
    /****/
    private String content;
    /****/
    private Integer sort;
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
    /****/
    private String spaceName;

    private Integer appraisalid;

    public Integer getAppraisalid() {
        return appraisalid;
    }

    public void setAppraisalid(Integer appraisalid) {
        this.appraisalid = appraisalid;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(Integer spaceId) {
        this.spaceId = spaceId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public String getSpaceName() {
        return spaceName;
    }

    public void setSpaceName(String spaceName) {
        this.spaceName = spaceName;
    }
}