package xin.xiaoer.modules.website.entity;

import java.io.Serializable;
import java.util.Date;

/**
 *
 *
 * @author daimingjian
 * @email 3088393266@qq.com
 * @date 2019-1-15 10:39:46
 */
public class LiveRoomUser implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final Integer ONLINE = 1;
    public static final Integer OFFLINE = 0;
    public static final Long NORMAL_USER = 1L;
    private Integer liveId;

    private Long userId;

    private Date joinAt;

    private Long liveRoleId;

    private Integer status;

    public Integer getLiveId() {
        return liveId;
    }

    public void setLiveId(Integer liveId) {
        this.liveId = liveId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getJoinAt() {
        return joinAt;
    }

    public void setJoinAt(Date joinAt) {
        this.joinAt = joinAt;
    }

    public Long getLiveRoleId() {
        return liveRoleId;
    }

    public void setLiveRoleId(Long liveRoleId) {
        this.liveRoleId = liveRoleId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
