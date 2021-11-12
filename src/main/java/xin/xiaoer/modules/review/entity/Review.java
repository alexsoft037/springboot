package xin.xiaoer.modules.review.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-07-27 21:24:14
 */
public class Review implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/****/
	private Long reviewId;
	/****/
	private Long articleId;
	/****/
	private String articleTitle;
	/****/
	private String articleTypeCode;
	/****/
	private String articleTypeTxt;
	/****/
	private String subject;
	/****/
	private String content;
	/****/
	private Long userId;
	/****/
	private String userName;
	/****/
	private Date createAt;
	/****/
	private String state;
	/****/
	private String avatar;

	/**
	 * 设置：
	 */
	public void setReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}
	/**
	 * 获取：
	 */
	public Long getReviewId() {
		return reviewId;
	}
	/**
	 * 设置：
	 */
	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}
	/**
	 * 获取：
	 */
	public Long getArticleId() {
		return articleId;
	}
	/**
	 * 设置：
	 */
	public void setArticleTypeCode(String articleTypeCode) {
		this.articleTypeCode = articleTypeCode;
	}
	/**
	 * 获取：
	 */
	public String getArticleTypeCode() {
		return articleTypeCode;
	}
	/**
	 * 设置：
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * 获取：
	 */
	public String getSubject() {
		return subject;
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
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：
	 */
	public Long getUserId() {
		return userId;
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

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public String getArticleTypeTxt() {
		return articleTypeTxt;
	}

	public void setArticleTypeTxt(String articleTypeTxt) {
		this.articleTypeTxt = articleTypeTxt;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
