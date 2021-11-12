package xin.xiaoer.modules.monitor.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-11-12 07:56:35
 */
public class CameraRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/****/
	private Long id;
	/****/
	private Integer deviceId;
	/****/
	private Long userId;
	/****/
	private String recordUrl;
	/****/
	private String threadId;
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
	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}
	/**
	 * 获取：
	 */
	public Integer getDeviceId() {
		return deviceId;
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
	public void setRecordUrl(String recordUrl) {
		this.recordUrl = recordUrl;
	}
	/**
	 * 获取：
	 */
	public String getRecordUrl() {
		return recordUrl;
	}
	/**
	 * 设置：
	 */
	public void setThreadId(String threadId) {
		this.threadId = threadId;
	}
	/**
	 * 获取：
	 */
	public String getThreadId() {
		return threadId;
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
