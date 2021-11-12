package xin.xiaoer.modules.spaceshow.entity;

import xin.xiaoer.modules.review.entity.Review;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-08-24 23:03:45
 */
public class SpaceShow implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/****/
	private Long showId;
	/****/
	private Integer spaceId;
	/****/
	private String spaceName;
	/****/
	private String showTypeCode;
	/****/
	private String title;
	/****/
	private String content;
	/****/
	private String image;
	/****/
	private String video;
	/****/
	private String address;
	/****/
	private String latitude;
	/****/
	private String longitude;
	/****/
	private String featuredYn;
	/****/
	private Integer readCount;
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
	/****/
	private String avatar;
	/****/
	private boolean likeYn;
	/****/
	private List<String> imageFiles;
	/****/
	private List<Map<String, String>> videoFiles;
	/****/
	private List<Review> reviews;

	private Integer likeCount;
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
	public void setShowTypeCode(String showTypeCode) {
		this.showTypeCode = showTypeCode;
	}
	/**
	 * 获取：
	 */
	public String getShowTypeCode() {
		return showTypeCode;
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
	public void setImage(String image) {
		this.image = image;
	}
	/**
	 * 获取：
	 */
	public String getImage() {
		return image;
	}
	/**
	 * 设置：
	 */
	public void setVideo(String video) {
		this.video = video;
	}
	/**
	 * 获取：
	 */
	public String getVideo() {
		return video;
	}
	/**
	 * 设置：
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取：
	 */
	public String getAddress() {
		return address;
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

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public List<String> getImageFiles() {
		return imageFiles;
	}

	public void setImageFiles(List<String> imageFiles) {
		this.imageFiles = imageFiles;
	}

	public List<Map<String, String>> getVideoFiles() {
		return videoFiles;
	}

	public void setVideoFiles(List<Map<String, String>> videoFiles) {
		this.videoFiles = videoFiles;
	}

	public boolean getLikeYn() {
		return likeYn;
	}

	public void setLikeYn(boolean likeYn) {
		this.likeYn = likeYn;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public String getSpaceName() {
		return spaceName;
	}

	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}
}
