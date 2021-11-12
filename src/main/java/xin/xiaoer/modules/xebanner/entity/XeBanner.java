package xin.xiaoer.modules.xebanner.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2018-07-12 18:47:51
 */
public class XeBanner implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/****/
	private Integer id;
	/****/
	private String bnUrl;
	/****/
	private String bnTypeCode;
	/****/
	private String bnName;
	/****/
	private String bnText;
	/****/
	private String bnLink;
	/****/
	private Integer htmlId;
	/****/
	private String articleType;
	/****/
	private String articleName;
	/****/
	private String articleStatus;
	/****/
	private Long articleId;
	/****/
	private String articleYN;
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
	public void setBnUrl(String bnUrl) {
		this.bnUrl = bnUrl;
	}
	/**
	 * 获取：
	 */
	public String getBnUrl() {
		return bnUrl;
	}
	/**
	 * 设置：
	 */
	public void setBnTypeCode(String bnTypeCode) {
		this.bnTypeCode = bnTypeCode;
	}
	/**
	 * 获取：
	 */
	public String getBnTypeCode() {
		return bnTypeCode;
	}
	/**
	 * 设置：
	 */
	public void setBnName(String bnName) {
		this.bnName = bnName;
	}
	/**
	 * 获取：
	 */
	public String getBnName() {
		return bnName;
	}
	/**
	 * 设置：
	 */
	public void setBnText(String bnText) {
		this.bnText = bnText;
	}
	/**
	 * 获取：
	 */
	public String getBnText() {
		return bnText;
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

	public String getBnLink() {
		return bnLink;
	}

	public void setBnLink(String bnLink) {
		this.bnLink = bnLink;
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public String getArticleType() {
		return articleType;
	}

	public void setArticleType(String articleType) {
		this.articleType = articleType;
	}

	public String getArticleYN() {
		return articleYN;
	}

	public void setArticleYN(String articleYN) {
		this.articleYN = articleYN;
	}

	public Integer getHtmlId() {
		return htmlId;
	}

	public void setHtmlId(Integer htmlId) {
		this.htmlId = htmlId;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public String getArticleTypeName() {
		return articleTypeName;
	}

	public void setArticleTypeName(String articleTypeName) {
		this.articleTypeName = articleTypeName;
	}

	public String getArticleStatus() {
		return articleStatus;
	}

	public void setArticleStatus(String articleStatus) {
		this.articleStatus = articleStatus;
	}
}
