package xin.xiaoer.modules.monitor.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-09-03 12:51:23
 */
public class UserChildren implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/****/
	private Long childrenId;
	/****/
	private Long userId;
	/****/
	private String name;
	/****/
	private String photo;
	/****/
	private String sex;
	/****/
	private String height;
	/****/
	private Date birthday;
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
	/****/
	private String userName;
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
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：
	 */
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	/**
	 * 获取：
	 */
	public String getPhoto() {
		return photo;
	}
	/**
	 * 设置：
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * 获取：
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * 设置：
	 */
	public void setHeight(String height) {
		this.height = height;
	}
	/**
	 * 获取：
	 */
	public String getHeight() {
		return height;
	}
	/**
	 * 设置：
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	/**
	 * 获取：
	 */
	public Date getBirthday() {
		return birthday;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
