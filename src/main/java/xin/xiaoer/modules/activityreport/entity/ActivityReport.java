package xin.xiaoer.modules.activityreport.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-07-24 17:57:36
 */
public class ActivityReport implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/****/
	private Integer reportId;
	/****/
	private String reportTypeCode;
	/****/
	private Integer spaceId;
	/****/
	private String title;
	/****/
	private String featuredImage;
	/****/
	private String content;
	/****/
	private String contentImage;
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

	private String createUser;

	private boolean byNewsMan;
	/****/
	private String reportType;
	/****/
	private boolean favouriteYn;
	/****/
	private boolean likeYn;
	/****/
	private Integer reviewCount;
	/****/
	private String authorName;
	/****/
	private String spaceName;
	/**
	 * 设置：
	 */
	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}
	/**
	 * 获取：
	 */
	public Integer getReportId() {
		return reportId;
	}
	/**
	 * 设置：
	 */
	public void setReportTypeCode(String reportTypeCode) {
		this.reportTypeCode = reportTypeCode;
	}
	/**
	 * 获取：
	 */
	public String getReportTypeCode() {
		return reportTypeCode;
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
	public void setContentImage(String contentImage) {
		this.contentImage = contentImage;
	}
	/**
	 * 获取：
	 */
	public String getContentImage() {
		return contentImage;
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

	public boolean getByNewsMan() {
		return byNewsMan;
	}

	public void setByNewsMan(boolean byNewsMan) {
		this.byNewsMan = byNewsMan;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public boolean getFavouriteYn() {
		return favouriteYn;
	}

	public void setFavouriteYn(boolean favouriteYn) {
		this.favouriteYn = favouriteYn;
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

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getSpaceName() {
		return spaceName;
	}

	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}
}
