package xin.xiaoer.modules.classroom.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-09-19 10:27:23
 */
public class LiveRoom implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/****/
	private Integer liveId;
	/****/
	private String title;

	private Integer spaceId;

	private String spaceName;
	/****/
	private String liveTypeCode;
	/****/
	private String liveTypeName;
	/****/
	private String content;
	/****/
	private String roomId;
	/****/
	private String featuredImage;
	/****/
	private String featuredUrl;
	/****/
	private String state;
	/****/
	private Date createAt;
	/****/
	private Long createBy;
	/****/
	private String createUser;
	/****/
	private boolean followYn;
	//新加
	private boolean likeYn;

	private Double liveDuration;

	private Date lastLiveAt;

	private String avatar;

	private String nickname;

	public Date getLastLiveAt() {
		return lastLiveAt;
	}

	public void setLastLiveAt(Date lastLiveAt) {
		this.lastLiveAt = lastLiveAt;
	}

	/**
	 * 设置：
	 */
	public void setLiveId(Integer liveId) {
		this.liveId = liveId;
	}
	/**
	 * 获取：
	 */
	public Integer getLiveId() {
		return liveId;
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
	public void setLiveTypeCode(String liveTypeCode) {
		this.liveTypeCode = liveTypeCode;
	}
	/**
	 * 获取：
	 */
	public String getLiveTypeCode() {
		return liveTypeCode;
	}
	/**
	 * 设置：
	 */
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	/**
	 * 获取：
	 */
	public String getRoomId() {
		return roomId;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean getFollowYn() {
		return followYn;
	}

	public boolean isFollowYn() {
		return followYn;
	}

	public void setFollowYn(boolean followYn) {
		this.followYn = followYn;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getLiveTypeName() {
		return liveTypeName;
	}

	public void setLiveTypeName(String liveTypeName) {
		this.liveTypeName = liveTypeName;
	}

	public String getFeaturedUrl() {
		return featuredUrl;
	}

	public void setFeaturedUrl(String featuredUrl) {
		this.featuredUrl = featuredUrl;
	}

	public String getSpaceName() {
		return spaceName;
	}

	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}

	public Integer getSpaceId() {
		return spaceId;
	}

	public void setSpaceId(Integer spaceId) {
		this.spaceId = spaceId;
	}

	public boolean isLikeYn() {
		return likeYn;
	}

	public void setLikeYn(boolean likeYn) {
		this.likeYn = likeYn;
	}

	public Double getLiveDuration() {
		return liveDuration;
	}

	public void setLiveDuration(Double liveDuration) {
		this.liveDuration = liveDuration;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
