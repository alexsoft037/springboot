package xin.xiaoer.modules.website.entity;

/**
 * @ClassName SysUserIntegralRankItem
 * @Description TODO 个人积分排名
 * @Author XiaoDong
 **/
public class SysUserIntegralRankItem {
    private Integer userId;
    private String name;
    private String nickname;
    private Double userIntegral;
    private Integer rank;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Double getUserIntegral() {
        return userIntegral;
    }

    public void setUserIntegral(Double userIntegral) {
        this.userIntegral = userIntegral;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}
