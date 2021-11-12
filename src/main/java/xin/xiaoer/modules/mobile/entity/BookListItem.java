package xin.xiaoer.modules.mobile.entity;

import java.io.Serializable;

public class BookListItem implements Serializable {
    private static final long serialVersionUID = 1L;

    /****/
    private Long bookId;
    /****/
    private String subject;
    /****/
    private String cover;
    /****/
    private String author;
    /****/
    private String categoryCode;
    /****/
    private String categoryName;
    /****/
    private Integer readCount;
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

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
