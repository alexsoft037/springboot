package xin.xiaoer.modules.donatespace.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2018-07-20 00:22:00
 */
public class DonateUserDetail implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/****/
	private Long id;
	/****/
	private Long itemId;
	/****/
	private String itemTitle;
	/****/
	private String featuredImage;
	/****/
	private Long userId;
	/****/
	private Double donateAmount;
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
	public void setDonateAmount(Double donateAmount) {
		this.donateAmount = donateAmount;
	}
	/**
	 * 获取：
	 */
	public Double getDonateAmount() {
		return donateAmount;
	}

	public String getItemTitle() {
		return itemTitle;
	}

	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	public String getFeaturedImage() {
		return featuredImage;
	}

	public void setFeaturedImage(String featuredImage) {
		this.featuredImage = featuredImage;
	}
}
