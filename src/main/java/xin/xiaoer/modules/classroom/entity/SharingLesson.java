package xin.xiaoer.modules.classroom.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-08-15 16:30:57
 */
public class SharingLesson implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/****/
	private Integer lessonId;
	/**课程名称**/
	private String title;
	/****/
	private Integer spaceId;
	/****/
	private String spaceName;
	/**课程简介**/
	private String introduction;
	/**课程类型**/
	private String categoryCode;
	/****/
	private String categoryName;
	/****/
	private String featuredImage;
	/****/
	private String videoFile;
	/**加权因子**/
	private Integer weight;
	/****/
	private String featuredYn;
	/****/
	private Integer readCount;
	/****/
	private Integer playCount;
	/****/
	private Integer reviewCount;
	/****/
	private Integer likeCount;
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

	private String avatar;

	private List<String> videoFiles;

	private boolean likeYn;
	/**
	 * 设置：
	 */
	public void setLessonId(Integer lessonId) {
		this.lessonId = lessonId;
	}
	/**
	 * 获取：
	 */
	public Integer getLessonId() {
		return lessonId;
	}
	/**
	 * 设置：课程名称
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：课程名称
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：课程简介
	 */
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	/**
	 * 获取：课程简介
	 */
	public String getIntroduction() {
		return introduction;
	}
	/**
	 * 设置：课程类型
	 */
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	/**
	 * 获取：课程类型
	 */
	public String getCategoryCode() {
		return categoryCode;
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
	public void setVideoFile(String videoFile) {
		this.videoFile = videoFile;
	}
	/**
	 * 获取：
	 */
	public String getVideoFile() {
		return videoFile;
	}
	/**
	 * 设置：加权因子
	 */
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	/**
	 * 获取：加权因子
	 */
	public Integer getWeight() {
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

	public Integer getPlayCount() {
		if (playCount == null || playCount < 0){
			playCount = 0;
			return 0;
		}
		return playCount;
	}

	public void setPlayCount(Integer playCount) {
		this.playCount = playCount;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateUser() {
		return createUser;
	}

	public List<String> getVideoFiles() {
		return videoFiles;
	}

	public void setVideoFiles(List<String> videoFiles) {
		this.videoFiles = videoFiles;
	}

	public Integer getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(Integer reviewCount) {
		this.reviewCount = reviewCount;
	}

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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

	public Integer getSpaceId() {
		return spaceId;
	}

	public void setSpaceId(Integer spaceId) {
		this.spaceId = spaceId;
	}

	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}

	public String getSpaceName() {
		return spaceName;
	}
}
