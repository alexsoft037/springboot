package xin.xiaoer.modules.spaceshow.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-08-24 23:03:45
 */
public class ActivityShow implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/****/
	private Integer id;
	/****/
	private Long activityId;
	/****/
	private Long showId;
	/****/
	private Date createAt;

	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
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
	public void setShowId(Long showId) {
		this.showId = showId;
	}
	/**
	 * 获取：
	 */
	public Long getShowId() {
		return showId;
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
