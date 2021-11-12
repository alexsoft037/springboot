package xin.xiaoer.modules.setting.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-10-04 01:38:29
 */
public class IntegralDetail extends Integral {
    private String articleTitle;

    private String articleContent;

    private String featuredImage;

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public String getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleTitle() {
        return articleTitle;
    }
}
