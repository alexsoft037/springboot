package xin.xiaoer.modules.spaceofthehall.entity;

import java.io.Serializable;

public class SpaceOfTheHall implements Serializable {
    private Integer id;
    private String title;//大厅标题
    private String url;//对应跳到哪
    private Integer status;//是(1)否(0)启用
    private String featuredImage;//对应sys_file表的唯一标识
    private String thumbnailImage;//微缩图标志

    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public void setThumbnailImage(String thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    public String getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
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


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
