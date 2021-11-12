package xin.xiaoer.modules.mobile.entity;

import java.io.Serializable;

public class PersonalBook implements Serializable {
    private static final long serialVersionUID = 1L;

    /****/
    private Long bookId;
    /****/
    private String subject;
    /****/
    private String cover;
    /****/
    private String categoryCode;
    /****/
    private String categoryName;
    /****/
    private String author;
    /****/
    private String numberOfPages;
    /****/
    private Integer readCount;
    /****/
    private String bookFile;

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

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(String numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public String getBookFile() {
        return bookFile;
    }

    public void setBookFile(String bookFile) {
        this.bookFile = bookFile;
    }
}
