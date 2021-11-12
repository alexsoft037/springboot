package xin.xiaoer.modules.donatespace.entity;

import io.swagger.models.auth.In;
import xin.xiaoer.entity.Article;

import java.util.ArrayList;
import java.util.Date;


/**
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2018-07-20 00:07:30
 */
public class DonateSpace extends Article {
    private static final long serialVersionUID = 1L;

    /****/
    private Long itemId;
    /****/
    private Integer spaceId;
    /****/
    private String dsTypeCode;
    /****/
    private String title;
    /****/
    private String description;
    /****/
    private Double targetAmount;
    /****/
    private String spaceImage;
    /****/
    private String introImage;
    /****/
    private String introContent;
    /****/
    private String processImage;
    /****/
    private String processContent;
    /****/
    private String address;
    /****/
    private String latitude;
    /****/
    private String longitude;
    /****/
    private String dsStatusCode;
    /****/
    private Integer readCount;
    /****/
    private String state;
    /****/
    private Date createAt;
    /****/
    private Long createBy;
    /****/
    private Date updateAt;
    /****/
    private Long updateBy;
    /****/
    private Double donateAmount;
    /****/
    private Integer donateUserCount;
    /****/
    private ArrayList<String> itemImages;
    /****/
    private String dsType;
    /****/
    private String dsStatus;
    /****/
    private String spaceName;

    /**
     * 设置：
     */
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    /**
     * 获取：
     */
    public Long getItemId() {
        return itemId;
    }
    /**
     * 设置：
     */
    public void setSpaceId(Integer spaceId) {
        this.spaceId = spaceId;
    }

    /**
     * 获取：
     */
    public Integer getSpaceId() {
        return spaceId;
    }

    /**
     * 设置：
     */
    public void setDsTypeCode(String dsTypeCode) {
        this.dsTypeCode = dsTypeCode;
    }

    /**
     * 获取：
     */
    public String getDsTypeCode() {
        return dsTypeCode;
    }

    /**
     * 设置：
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取：
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置：
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取：
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置：
     */
    public void setTargetAmount(Double targetAmount) {
        this.targetAmount = targetAmount;
    }

    /**
     * 获取：
     */
    public Double getTargetAmount() {
        return targetAmount;
    }

    /**
     * 设置：
     */
    public void setSpaceImage(String spaceImage) {
        this.spaceImage = spaceImage;
    }

    /**
     * 获取：
     */
    public String getSpaceImage() {
        return spaceImage;
    }

    public String getIntroImage() {
        return introImage;
    }

    public void setIntroImage(String introImage) {
        this.introImage = introImage;
    }

    public String getIntroContent() {
        return introContent;
    }

    public void setIntroContent(String introContent) {
        this.introContent = introContent;
    }

    public String getProcessImage() {
        return processImage;
    }

    public void setProcessImage(String processImage) {
        this.processImage = processImage;
    }

    public String getProcessContent() {
        return processContent;
    }

    public void setProcessContent(String processContent) {
        this.processContent = processContent;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 设置：
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * 获取：
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * 设置：
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * 获取：
     */
    public String getLongitude() {
        return longitude;
    }

    public String getDsStatusCode() {
        return dsStatusCode;
    }

    public void setDsStatusCode(String dsStatusCode) {
        this.dsStatusCode = dsStatusCode;
    }

    public Integer getReadCount() {
        if (readCount == null || readCount < 0){
            readCount = 0;
            return 0;
        }
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    /**
     * 设置：
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 获取：
     */
    public String getState() {
        return state;
    }

    /**
     * 设置：
     */
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /**
     * 获取：
     */
    public Date getCreateAt() {
        return createAt;
    }

    /**
     * 设置：
     */
    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    /**
     * 获取：
     */
    public Long getCreateBy() {
        return createBy;
    }

    /**
     * 设置：
     */
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    /**
     * 获取：
     */
    public Date getUpdateAt() {
        return updateAt;
    }

    /**
     * 设置：
     */
    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 获取：
     */
    public Long getUpdateBy() {
        return updateBy;
    }

    public Integer getDonateUserCount() {
        return donateUserCount;
    }

    public void setDonateUserCount(Integer donateUserCount) {
        this.donateUserCount = donateUserCount;
    }

    public Double getDonateAmount() {
        return donateAmount;
    }

    public void setDonateAmount(Double donateAmount) {
        this.donateAmount = donateAmount;
    }

    public ArrayList<String> getItemImages() {
        return itemImages;
    }

    public void setItemImages(ArrayList<String> itemImages) {
        this.itemImages = itemImages;
    }

    public String getDsType() {
        return dsType;
    }

    public void setDsType(String dsType) {
        this.dsType = dsType;
    }

    public String getDsStatus() {
        return dsStatus;
    }

    public void setDsStatus(String dsStatus) {
        this.dsStatus = dsStatus;
    }

    public String getSpaceName() {
        return spaceName;
    }

    public void setSpaceName(String spaceName) {
        this.spaceName = spaceName;
    }
}
