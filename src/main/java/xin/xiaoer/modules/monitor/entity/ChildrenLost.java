package xin.xiaoer.modules.monitor.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-09-03 12:51:23
 */
public class ChildrenLost implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/****/
	private Integer id;
	/****/
	private Integer spaceId;
	/****/
	private String spaceName;
	/****/
	private Long childrenId;
	/****/
	private String subject;
	/****/
	private String description;
	/****/
	private Date lostTime;
	/****/
	private String latitude;
	/****/
	private String longitude;
	/****/
	private String photo;
	/****/
	private String state;
	/****/
	private Date createAt;
	/****/
	private Long createBy;

	private String name;

	private Integer age;

	private Date birthday;

	private String height;

	private String userName;

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
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * 获取：
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * 设置：
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取：
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置：
	 */
	public void setLostTime(Date lostTime) {
		this.lostTime = lostTime;
	}
	/**
	 * 获取：
	 */
	public Date getLostTime() {
		return lostTime;
	}
	/**
	 * 设置：
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	/**
	 * 获取：
	 */
	public String getLatitude() {
		return latitude;
	}
	/**
	 * 设置：
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	/**
	 * 获取：
	 */
	public String getLongitude() {
		return longitude;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getAge() {
		if (birthday == null){
			return 0;
		}
		Date today = new Date();
		Calendar calendar1 = new GregorianCalendar();
		calendar1.setTime(today);
		Calendar calendar2 = new GregorianCalendar();
		calendar2.setTime(birthday);
		this.age = calendar1.get(Calendar.YEAR) - calendar2.get(Calendar.YEAR);
		return age;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}

	public String getSpaceName() {
		return spaceName;
	}

	public void setSpaceId(Integer spaceId) {
		this.spaceId = spaceId;
	}

	public Integer getSpaceId() {
		return spaceId;
	}
}
