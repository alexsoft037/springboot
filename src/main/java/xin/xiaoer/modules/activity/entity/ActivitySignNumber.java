package xin.xiaoer.modules.activity.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-10-04 00:05:33
 */
public class ActivitySignNumber implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/****/
	private Integer activityId;
	/****/
	private String signNumber;

	/**
	 * 设置：
	 */
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	/**
	 * 获取：
	 */
	public Integer getActivityId() {
		return activityId;
	}
	/**
	 * 设置：
	 */
	public void setSignNumber(String signNumber) {
		this.signNumber = signNumber;
	}
	/**
	 * 获取：
	 */
	public String getSignNumber() {
		return signNumber;
	}
}
