package xin.xiaoer.modules.setting.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-09-23 20:48:27
 */
public class Contact implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/****/
	private Long contactId;
	/****/
	private String title;
	/****/
	private String content;
	/****/
	private String attachment;
	/****/
	private String contactInfo;
	/****/
	private String state;
	/****/
	private Date createAt;
	/****/
	private Long createBy;

	private String userName;

	/**
	 * 设置：
	 */
	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}
	/**
	 * 获取：
	 */
	public Long getContactId() {
		return contactId;
	}
	/**
	 * 设置：
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：
	 */
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	/**
	 * 获取：
	 */
	public String getAttachment() {
		return attachment;
	}
	/**
	 * 设置：
	 */
	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}
	/**
	 * 获取：
	 */
	public String getContactInfo() {
		return contactInfo;
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

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}
}
