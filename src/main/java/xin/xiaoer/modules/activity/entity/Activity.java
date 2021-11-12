package xin.xiaoer.modules.activity.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-07-23 20:59:06
 */
public class Activity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/****/
	private Integer activityId;
	/****/
	private Integer spaceId;
	/****/
	private String activityTypeCode;
	/****/
	private String title;
	/****/
	private String content;
	/****/
	private String featuredImage;
	/****/
	private Date periodFrom;
	/****/
	private Date periodTo;
	/****/
	private Date registerEnd;
	/****/
	private String activityStatusCode;
	/****/
	private Integer numberOfPeople;
	/****/
	private Integer readCount;
	/****/
	private String location;

	private String district;

	private String city;

	private String province;

	private String street;

	private String streetNumber;
	/****/
	private String latitude;
	/****/
	private String longitude;
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

	private String createUser;

	private String contactPhone;

	private String contactName;

	private String activityType;

	private String activityStatus;

	private Integer totalUsers;

	private boolean attendYN;

	private String weight;

	private String integral;

	private String featuredYn;

	private boolean likeYn;

	private Integer reviewCount;

	private boolean signYn;

	private String spaceName;
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
	public Integer getSpaceId() {
		return spaceId;
	}
	/**
	 * 获取：
	 */
	public void setSpaceId(Integer spaceId) {
		this.spaceId = spaceId;
	}
	/**
	 * 设置：
	 */
	public void setActivityTypeCode(String activityTypeCode) {
		this.activityTypeCode = activityTypeCode;
	}
	/**
	 * 获取：
	 */
	public String getActivityTypeCode() {
		return activityTypeCode;
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
	public void setPeriodFrom(Date periodFrom) {
		this.periodFrom = periodFrom;
	}
	/**
	 * 获取：
	 */
	public Date getPeriodFrom() {
		return periodFrom;
	}
	/**
	 * 设置：
	 */
	public void setPeriodTo(Date periodTo) {
		this.periodTo = periodTo;
	}
	/**
	 * 获取：
	 */
	public Date getPeriodTo() {
		return periodTo;
	}
	/**
	 * 设置：
	 */
	public void setRegisterEnd(Date registerEnd) {
		this.registerEnd = registerEnd;
	}
	/**
	 * 获取：
	 */
	public Date getRegisterEnd() {
		return registerEnd;
	}
	/**
	 * 设置：
	 */
	public void setActivityStatusCode(String activityStatusCode) {
		this.activityStatusCode = activityStatusCode;
	}
	/**
	 * 获取：
	 */
	public String getActivityStatusCode() {
		return activityStatusCode;
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

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateUser() {
		return createUser;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
	}

	public void setTotalUsers(Integer totalUsers) {
		this.totalUsers = totalUsers;
	}

	public Integer getTotalUsers() {
		return totalUsers;
	}

	public boolean getAttendYN() {
		return attendYN;
	}

	public void setAttendYN(boolean attendYN) {
		this.attendYN = attendYN;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getFeaturedYn() {
		return featuredYn;
	}

	public void setFeaturedYn(String featuredYn) {
		this.featuredYn = featuredYn;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public boolean getLikeYn() {
		return likeYn;
	}

	public void setLikeYn(boolean likeYn) {
		this.likeYn = likeYn;
	}

	public Integer getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(Integer reviewCount) {
		this.reviewCount = reviewCount;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public boolean getSignYn() {
		return signYn;
	}

	public void setSignYn(boolean signYn) {
		this.signYn = signYn;
	}

	public String getIntegral() {
		return integral;
	}

	public void setIntegral(String integral) {
		this.integral = integral;
	}

	public String getSpaceName() {
		return spaceName;
	}

	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}
}
