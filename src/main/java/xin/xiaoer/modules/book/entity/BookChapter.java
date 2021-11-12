package xin.xiaoer.modules.book.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-08-09 10:26:24
 */
public class BookChapter implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/****/
	private Long chapterId;
	/****/
	private String bookFile;
	/****/
	private String chapterIndex;
	/****/
	private String title;
	/****/
	private String content;
	/****/
	private Integer index;
	/****/
	private Integer pageNumber;
	/****/
	private Integer textCount;
	/****/
	private String bookSubject;
	/****/
	private Long bookId;
	/**
	 * 设置：
	 */
	public void setChapterId(Long chapterId) {
		this.chapterId = chapterId;
	}
	/**
	 * 获取：
	 */
	public Long getChapterId() {
		return chapterId;
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
	public void setChapterIndex(String chapterIndex) {
		this.chapterIndex = chapterIndex;
	}
	/**
	 * 获取：
	 */
	public String getChapterIndex() {
		return chapterIndex;
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
	public void setIndex(Integer index) {
		this.index = index;
	}
	/**
	 * 获取：
	 */
	public Integer getIndex() {
		return index;
	}
	/**
	 * 设置：
	 */
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	/**
	 * 获取：
	 */
	public Integer getPageNumber() {
		return pageNumber;
	}
	/**
	 * 设置：
	 */
	public void setTextCount(Integer textCount) {
		this.textCount = textCount;
	}
	/**
	 * 获取：
	 */
	public Integer getTextCount() {
		return textCount;
	}

	public String getBookSubject() {
		return bookSubject;
	}

	public void setBookSubject(String bookSubject) {
		this.bookSubject = bookSubject;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}
}
