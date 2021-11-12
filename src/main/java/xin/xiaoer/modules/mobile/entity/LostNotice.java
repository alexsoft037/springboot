package xin.xiaoer.modules.mobile.entity;

import java.io.Serializable;
import java.util.Date;

public class LostNotice implements Serializable {
    private static final long serialVersionUID = 1L;
    /****/
    private Integer id;
    /****/
    private String subject;
    /****/
    private Date lostTime;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getLostTime() {
        return lostTime;
    }

    public void setLostTime(Date lostTime) {
        this.lostTime = lostTime;
    }
}
