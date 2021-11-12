package xin.xiaoer.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName PushMessage
 * @Description TODO
 * @Author XiaoDong
 **/
public class PushMessage implements Serializable {
    private Integer msgId;
    private String message;
    private Long fromUserId;
    private Date createAt;

    public Integer getMsgId() {
        return msgId;
    }

    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
