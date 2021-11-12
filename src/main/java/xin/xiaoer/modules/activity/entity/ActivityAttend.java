package xin.xiaoer.modules.activity.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-07-24 12:11:49
 */
public class ActivityAttend implements Serializable {
	private static final long serialVersionUID = 1L;

	/****/
	private Long id;
	/****/
	private Long activityId;
	/****/
	private Long userId;
	/****/
	private String state;
	/****/
	private Date createTime;

	private String userName;

	private String avatar;

	private String activityTitle;

	/**
	 * 设置：
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 获取：
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 设置：
	 */
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	/**
	 * 获取：
	 */
	public Long getActivityId() {
		return activityId;
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
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：
	 */
	public Date getCreateTime() {
		return createTime;
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

	public String getActivityTitle() {
		return activityTitle;
	}

	public void setActivityTitle(String activityTitle) {
		this.activityTitle = activityTitle;
	}
}
