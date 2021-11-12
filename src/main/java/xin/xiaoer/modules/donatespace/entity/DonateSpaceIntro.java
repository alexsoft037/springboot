package xin.xiaoer.modules.donatespace.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2018-07-20 00:13:18
 */
public class DonateSpaceIntro implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/****/
	private Long itemId;
	/****/
	private String introImage;
	/****/
	private String introContent;
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
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	/**
	 * 获取：
	 */
	public Long getItemId() {
		return itemId;
	}
	/**
	 * 设置：
	 */
	public void setIntroImage(String introImage) {
		this.introImage = introImage;
	}
	/**
	 * 获取：
	 */
	public String getIntroImage() {
		return introImage;
	}
	/**
	 * 设置：
	 */
	public void setIntroContent(String introContent) {
		this.introContent = introContent;
	}
	/**
	 * 获取：
	 */
	public String getIntroContent() {
		return introContent;
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
}
