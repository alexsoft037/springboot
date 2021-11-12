package xin.xiaoer.modules.website.entity;

/**
 * @ClassName SpaceRankItem
 * @Description TODO
 * @Author XiaoDong
 **/
public class SpaceRankItem {
    private Integer spaceId;
    private String spaceName;//空间名字
    private Double spaceIntegral;//积分
    private Integer rank;//排名
    private String region;//区域
    private Integer peopleCount;//空间人数
    private Integer donateCount;//捐赠数量
    private Integer donateSpaceCount;//公益数量

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Integer getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(Integer peopleCount) {
        this.peopleCount = peopleCount;
    }

    public Integer getDonateCount() {
        return donateCount;
    }

    public void setDonateCount(Integer donateCount) {
        this.donateCount = donateCount;
    }

    public Integer getDonateSpaceCount() {
        return donateSpaceCount;
    }

    public void setDonateSpaceCount(Integer donateSpaceCount) {
        this.donateSpaceCount = donateSpaceCount;
    }

    public Integer getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(Integer spaceId) {
        this.spaceId = spaceId;
    }

    public String getSpaceName() {
        return spaceName;
    }

    public void setSpaceName(String spaceName) {
        this.spaceName = spaceName;
    }

    public Double getSpaceIntegral() {
        return spaceIntegral;
    }

    public void setSpaceIntegral(Double spaceIntegral) {
        this.spaceIntegral = spaceIntegral;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}
