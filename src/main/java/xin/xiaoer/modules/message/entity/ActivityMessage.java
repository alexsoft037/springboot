package xin.xiaoer.modules.message.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author DaiMingJian
 * @email 3088393266@qq.com
 * @date 2018/12/21
 */
public class ActivityMessage implements Serializable{
    private static final long serialVersionUID = 1L;

    private Integer messageId;

    private String name;

    private String phoneNo;

    private String content;

    private Date createAt;

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
