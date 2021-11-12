package xin.xiaoer.modules.setting.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-10-04 01:38:29
 */
public class Integral implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final Double ACTIVITY_DEFAULT = 40.0;
	public static final Double ACTIVITY_MAX = 200.0;
	public static final Double SPACESHOW_DEFAULT = 10.0;
	public static final Double SPACESHOW_DAY_MAX = 30.0;
	/****/
	private Long id;
	/****/
	private String title;
	/****/
	private Long userId;
	/****/
	private String integral;
	/****/
	private String articleTypeCode;
	/****/
	private Long articleId;
	/****/
	private Date createAt;

	/**
	 * 设置：
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Long getId() {
		return id;
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
	public void setIntegral(String integral) {
		this.integral = integral;
	}
	/**
	 * 获取：
	 */
	public String getIntegral() {
		return integral;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
