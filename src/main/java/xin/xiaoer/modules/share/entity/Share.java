package xin.xiaoer.modules.share.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-10-20 21:15:57
 */
public class Share implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/****/
	private Long shareId;
	/****/
	private Long userId;
	/****/
	private String articleTypeCode;
	/****/
	private Long articleId;
	/****/
	private Date createAt;

	/**
	 * 设置：
	 */
	public void setShareId(Long shareId) {
		this.shareId = shareId;
	}
	/**
	 * 获取：
	 */
	public Long getShareId() {
		return shareId;
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
