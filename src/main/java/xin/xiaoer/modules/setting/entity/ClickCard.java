package xin.xiaoer.modules.setting.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-10-04 01:18:30
 */
public class ClickCard implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/****/
	private Long userId;
	/****/
	private Date lastClickDt;
	/****/
	private Integer clickCount;

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
	public void setLastClickDt(Date lastClickDt) {
		this.lastClickDt = lastClickDt;
	}
	/**
	 * 获取：
	 */
	public Date getLastClickDt() {
		return lastClickDt;
	}
	/**
	 * 设置：
	 */
	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}
	/**
	 * 获取：
	 */
	public Integer getClickCount() {
		return clickCount;
	}
}
