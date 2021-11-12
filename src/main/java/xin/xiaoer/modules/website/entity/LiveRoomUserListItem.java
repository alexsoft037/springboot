package xin.xiaoer.modules.website.entity;


import java.io.Serializable;

/**
 *
 *
 * @author daimingjian
 * @email 3088393266@qq.com
 * @date 2019-1-15 10:39:46
 */

public class LiveRoomUserListItem implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long liveRoleId;

    private Long userId;

    private String roleName;

    private String avatar;

    private String nickname;

    public Long getLiveRoleId() {
        return liveRoleId;
    }

    public void setLiveRoleId(Long liveRoleId) {
        this.liveRoleId = liveRoleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
