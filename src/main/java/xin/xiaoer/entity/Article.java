package xin.xiaoer.entity;

import xin.xiaoer.modules.comment.entity.Comment;

import java.io.Serializable;
import java.util.List;

public class Article implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String DONATE_SPACE = "AT0001";
    public static final String SPACE_HEADLINE = "AT0002";
    public static final String ACTIVITY_REPORT = "AT0003";
    public static final String ACTIVITY = "AT0004";
    public static final String SPACE_STORY = "AT0005";
    public static final String BOOK = "AT0006";
    public static final String LIVE_LESSON = "AT0007";
    public static final String SHARE_LESSON = "AT0008";
    public static final String STUDY_ROOM = "AT0009";
    public static final String SPACE_SHOW = "AT0010";
    public static final String REVIEW = "AT0011";
    public static final String INTEGRAL = "AT0012";
    public static final String REPORT_LIKE = "AT0013";
    public static final String SHARE_LIKE = "AT0014";
    public static final String REPORT_REVIEW = "AT0015";
    public static final String SHARE_REVIEW = "AT0016";
    public static final String SHARE_READ = "AT0017";
    public static final String USE_APP = "AT0018";
    //黄小东
    public static final String DONATE_WEB = "AT0019";

    protected Integer commentCount;

    protected Integer likeCount;

    protected List<Comment> commentList;

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}
