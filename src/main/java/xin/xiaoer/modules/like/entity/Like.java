package xin.xiaoer.modules.like.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-07-23 09:58:53
 */
public class Like implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/****/
	private Long likeId;
	/****/
	private Long articleId;
	/****/
	private String articleTypeCode;
	/****/
	private Long userId;
	/****/
	private Date createAt;

	/**
	 * 设置：
	 */
	public void setLikeId(Long likeId) {
		this.likeId = likeId;
	}
	/**
	 * 获取：
	 */
	public Long getLikeId() {
		return likeId;
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
}
