package xin.xiaoer.modules.book.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-07-30 17:08:46
 */
public class Book implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/****/
	private Long bookId;
	/****/
	private String categoryCode;
	/****/
	private String categoryName;
	/****/
	private ArrayList categoryCodes;
	/****/
	private Integer spaceId;
	/****/
	private String subject;

	private String introduction;
	/****/
	private String cover;
	/****/
	private String bookFile;
	/****/
	private String author;
	/****/
	private String publisher;
	/****/
	private String publishingYear;
	/****/
	private String numberOfIssues;
	/****/
	private String numberOfPages;
	/****/
	private String isbn;
	/****/
	private Integer readCount;
	/****/
	private String weight;
	/****/
	private String integral;
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

	private boolean hasChapters;

	private Integer countChapter;

	private boolean bookmarkYn;

	private boolean likeYn;

	private Integer reviewCount;

	private String spaceName;
	/**
	 * 设置：
	 */
	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}
	/**
	 * 获取：
	 */
	public Long getBookId() {
		return bookId;
	}
	/**
	 * 设置：
	 */
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	/**
	 * 获取：
	 */
	public String getCategoryCode() {
		return categoryCode;
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
	public void setCover(String cover) {
		this.cover = cover;
	}
	/**
	 * 获取：
	 */
	public String getCover() {
		return cover;
	}
	/**
	 * 设置：
	 */
	public void setBookFile(String bookFile) {
		this.bookFile = bookFile;
	}
	/**
	 * 获取：
	 */
	public String getBookFile() {
		return bookFile;
	}
	/**
	 * 设置：
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	/**
	 * 获取：
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * 设置：
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	/**
	 * 获取：
	 */
	public String getPublisher() {
		return publisher;
	}
	/**
	 * 设置：
	 */
	public void setPublishingYear(String publishingYear) {
		this.publishingYear = publishingYear;
	}
	/**
	 * 获取：
	 */
	public String getPublishingYear() {
		return publishingYear;
	}
	/**
	 * 设置：
	 */
	public void setNumberOfIssues(String numberOfIssues) {
		this.numberOfIssues = numberOfIssues;
	}
	/**
	 * 获取：
	 */
	public String getNumberOfIssues() {
		return numberOfIssues;
	}
	/**
	 * 设置：
	 */
	public void setNumberOfPages(String numberOfPages) {
		this.numberOfPages = numberOfPages;
	}
	/**
	 * 获取：
	 */
	public String getNumberOfPages() {
		return numberOfPages;
	}
	/**
	 * 设置：
	 */
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	/**
	 * 获取：
	 */
	public String getIsbn() {
		return isbn;
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

	public ArrayList getCategoryCodes() {
		return categoryCodes;
	}

	public void setCategoryCodes(ArrayList categoryCodes) {
		this.categoryCodes = categoryCodes;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public boolean getHasChapters() {
		return hasChapters;
	}

	public void setHasChapters(boolean hasChapters) {
		this.hasChapters = hasChapters;
	}

	public Integer getCountChapter() {
		return countChapter;
	}

	public void setCountChapter(Integer countChapter) {
		this.countChapter = countChapter;
	}

	public boolean getBookmarkYn() {
		return bookmarkYn;
	}

	public void setBookmarkYn(boolean bookmarkYn) {
		this.bookmarkYn = bookmarkYn;
	}

	public Integer getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(Integer reviewCount) {
		this.reviewCount = reviewCount;
	}

	public boolean getLikeYn() {
		return likeYn;
	}

	public void setLikeYn(boolean likeYn) {
		this.likeYn = likeYn;
	}

	public void setIntegral(String integral) {
		this.integral = integral;
	}

	public String getIntegral() {
		return integral;
	}

	public String getSpaceName() {
		return spaceName;
	}

	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}
}
