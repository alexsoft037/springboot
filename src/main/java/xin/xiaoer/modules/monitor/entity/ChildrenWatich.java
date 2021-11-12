package xin.xiaoer.modules.monitor.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-09-04 11:22:31
 */
public class ChildrenWatich implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/****/
	private Long watchId;
	/****/
	private Long childrenId;
	/****/
	private String deviceId;
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
	public void setWatchId(Long watchId) {
		this.watchId = watchId;
	}
	/**
	 * 获取：
	 */
	public Long getWatchId() {
		return watchId;
	}
	/**
	 * 设置：
	 */
	public void setChildrenId(Long childrenId) {
		this.childrenId = childrenId;
	}
	/**
	 * 获取：
	 */
	public Long getChildrenId() {
		return childrenId;
	}
	/**
	 * 设置：
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	/**
	 * 获取：
	 */
	public String getDeviceId() {
		return deviceId;
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
