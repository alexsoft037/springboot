package xin.xiaoer.modules.classroom.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-10-06 13:14:35
 */
public class LiveRoomMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/****/
	private Integer liveId;
	/****/
	private Long senderId;
	/****/
	private String message;
	/****/
	private String userName;
	/****/
	private String avatar;
	/****/
	private Date sendAt;

	/**
	 * 设置：
	 */
	public void setLiveId(Integer liveId) {
		this.liveId = liveId;
	}
	/**
	 * 获取：
	 */
	public Integer getLiveId() {
		return liveId;
	}
	/**
	 * 设置：
	 */
	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}
	/**
	 * 获取：
	 */
	public Long getSenderId() {
		return senderId;
	}
	/**
	 * 设置：
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * 获取：
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * 设置：
	 */
	public void setSendAt(Date sendAt) {
		this.sendAt = sendAt;
	}
	/**
	 * 获取：
	 */
	public Date getSendAt() {
		return sendAt;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
}
