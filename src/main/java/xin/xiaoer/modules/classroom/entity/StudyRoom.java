package xin.xiaoer.modules.classroom.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-08-20 13:04:31
 */
public class StudyRoom implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/****/
	private Integer roomId;
	/****/
	private Integer spaceId;
	/****/
	private String spaceName;
	/****/
	private String srTypeCode;
	/****/
	private String srTypeName;
	/****/
	private String title;
	/****/
	private String content;
	/****/
	private String featuredImage;
	/****/
	private Date startTime;
	/****/
	private String srStatusCode;
	/****/
	private Integer numberOfPeople;
	/****/
	private String integral;
	/****/
	private Integer readCount;
	/****/
	private Integer totalAttends;
	/****/
	private String location;
	/****/
	private String latitude;
	/****/
	private String longitude;
	/****/
	private String weight;
	/****/
	private String featuredYn;
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
	private String createUser;

	private String srStatusName;

	private boolean attendYN;

	private boolean likeYn;

	private Integer reviewCount;
	/**
	 * 设置：
	 */
	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}
	/**
	 * 获取：
	 */
	public Integer getRoomId() {
		return roomId;
	}
	/**
	 * 设置：
	 */
	public void setSpaceId(Integer spaceId) {
		this.spaceId = spaceId;
	}
	/**
	 * 获取：
	 */
	public Integer getSpaceId() {
		return spaceId;
	}
	/**
	 * 设置：
	 */
	public void setSrTypeCode(String srTypeCode) {
		this.srTypeCode = srTypeCode;
	}
	/**
	 * 获取：
	 */
	public String getSrTypeCode() {
		return srTypeCode;
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
	public void setFeaturedImage(String featuredImage) {
		this.featuredImage = featuredImage;
	}
	/**
	 * 获取：
	 */
	public String getFeaturedImage() {
		return featuredImage;
	}
	/**
	 * 设置：
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * 获取：
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * 设置：
	 */
	public void setSrStatusCode(String srStatusCode) {
		this.srStatusCode = srStatusCode;
	}
	/**
	 * 获取：
	 */
	public String getSrStatusCode() {
		return srStatusCode;
	}
	/**
	 * 设置：
	 */
	public void setNumberOfPeople(Integer numberOfPeople) {
		this.numberOfPeople = numberOfPeople;
	}
	/**
	 * 获取：
	 */
	public Integer getNumberOfPeople() {
		return numberOfPeople;
	}
	/**
	 * 设置：
	 */
	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}
	/**
	 * 获取：
	 */
	public Integer getReadCount() {
		if (readCount == null || readCount < 0){
			readCount = 0;
			return 0;
		}
		return readCount;
	}
	/**
	 * 设置：
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * 获取：
	 */
	public String getLocation() {
		return location;
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
	public void setWeight(String weight) {
		this.weight = weight;
	}
	/**
	 * 获取：
	 */
	public String getWeight() {
		return weight;
	}
	/**
	 * 设置：
	 */
	public void setFeaturedYn(String featuredYn) {
		this.featuredYn = featuredYn;
	}
	/**
	 * 获取：
	 */
	public String getFeaturedYn() {
		return featuredYn;
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

	public String getIntegral() {
		return integral;
	}

	public void setIntegral(String integral) {
		this.integral = integral;
	}

	public String getSrTypeName() {
		return srTypeName;
	}

	public void setSrTypeName(String srTypeName) {
		this.srTypeName = srTypeName;
	}

	public Integer getTotalAttends() {
		return totalAttends;
	}

	public void setTotalAttends(Integer totalAttends) {
		this.totalAttends = totalAttends;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getSrStatusName() {
		return srStatusName;
	}

	public void setSrStatusName(String srStatusName) {
		this.srStatusName = srStatusName;
	}

	public boolean getAttendYN() {
		return attendYN;
	}

	public void setAttendYN(boolean attendYN) {
		this.attendYN = attendYN;
	}

	public void setLikeYn(boolean likeYn) {
		this.likeYn = likeYn;
	}

	public boolean getLikeYn() {
		return likeYn;
	}

	public void setReviewCount(Integer reviewCount) {
		this.reviewCount = reviewCount;
	}

	public Integer getReviewCount() {
		return reviewCount;
	}

	public String getSpaceName() {
		return spaceName;
	}

	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}
}
