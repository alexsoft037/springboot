package xin.xiaoer.modules.setting.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-10-16 22:21:41
 */
public class Notification implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String LIVE_ROOM_TITLE = "New Live Posted";

	public static final String PUSH_LOGOUT_TITLE = "您已从其他设备登录!";
	/****/
	private Long noteId;
	/****/
	private Long userId;
	/****/
	private String title;
	/****/
	private String content;
	/****/
	private String readYn;
	/****/
	private String arrivedYn;
	/****/
	private Date createAt;

	/**
	 * 设置：
	 */
	public void setNoteId(Long noteId) {
		this.noteId = noteId;
	}
	/**
	 * 获取：
	 */
	public Long getNoteId() {
		return noteId;
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
	public void setReadYn(String readYn) {
		this.readYn = readYn;
	}
	/**
	 * 获取：
	 */
	public String getReadYn() {
		return readYn;
	}
	/**
	 * 设置：
	 */
	public void setArrivedYn(String arrivedYn) {
		this.arrivedYn = arrivedYn;
	}
	/**
	 * 获取：
	 */
	public String getArrivedYn() {
		return arrivedYn;
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
