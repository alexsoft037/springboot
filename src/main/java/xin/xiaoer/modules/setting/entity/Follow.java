package xin.xiaoer.modules.setting.entity;

import xin.xiaoer.entity.SysUser;

import java.io.Serializable;



/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-10-02 12:05:02
 */
public class Follow implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/****/
	private Long id;
	/****/
	private Long userId;
	/****/
	private Long followId;
	/****/
	private SysUser user;
	/****/
	private String avatar;
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
	public void setFollowId(Long followId) {
		this.followId = followId;
	}
	/**
	 * 获取：
	 */
	public Long getFollowId() {
		return followId;
	}

	public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}
}
