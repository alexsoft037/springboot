package xin.xiaoer.modules.advert.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2018-07-18 11:36:03
 */
public class XeAdvert implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/****/
	private Integer id;
	/****/
	private String adUrl;
	/****/
	private String adName;
	/****/
	private String adContent;
	/****/
	private String adImagePath;
	/****/
	private String articleType;
	/****/
	private String articleName;
	/****/
	private String articleStatus;
	/****/
	private Long articleId;
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
	private String articleTypeName;
	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：
	 */
	public void setAdUrl(String adUrl) {
		this.adUrl = adUrl;
	}
	/**
	 * 获取：
	 */
	public String getAdUrl() {
		return adUrl;
	}
	/**
	 * 设置：
	 */
	public void setAdName(String adName) {
		this.adName = adName;
	}
	/**
	 * 获取：
	 */
	public String getAdName() {
		return adName;
	}
	/**
	 * 设置：
	 */
	public void setAdContent(String adContent) {
		this.adContent = adContent;
	}
	/**
	 * 获取：
	 */
	public String getAdContent() {
		return adContent;
	}
	/**
	 * 设置：
	 */
	public void setAdImagePath(String adImagePath) {
		this.adImagePath = adImagePath;
	}
	/**
	 * 获取：
	 */
	public String getAdImagePath() {
		return adImagePath;
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

	public String getArticleStatus() {
		return articleStatus;
	}

	public void setArticleStatus(String articleStatus) {
		this.articleStatus = articleStatus;
	}


	public void setArticleType(String articleType) {
		this.articleType = articleType;
	}

	public String getArticleType() {
		return articleType;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleTypeName(String articleTypeName) {
		this.articleTypeName = articleTypeName;
	}

	public String getArticleTypeName() {
		return articleTypeName;
	}
}
