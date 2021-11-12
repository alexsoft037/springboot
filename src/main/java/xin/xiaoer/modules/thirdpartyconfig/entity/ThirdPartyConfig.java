package xin.xiaoer.modules.thirdpartyconfig.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2018-07-08 18:35:30
 */
public class ThirdPartyConfig implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/****/
	private Integer id;
	/****/
	private String tpTypeCode;
	/****/
	private String tpTypeName;
	/****/
	private String tpUrl;
	/****/
	private String tpUser;
	/****/
	private String tpPassword;
	/****/
	private String tpRemark;
	/****/
	private String state;
	/****/
	private Date createTime;
	/****/
	private Long createBy;
	/****/
	private Date updateTime;
	/****/
	private Long updateBy;

	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：
	 */
	public void setTpTypeCode(String tpTypeCode) {
		this.tpTypeCode = tpTypeCode;
	}
	/**
	 * 获取：
	 */
	public String getTpTypeCode() {
		return tpTypeCode;
	}
	/**
	 * 设置：
	 */
	public void setTpUrl(String tpUrl) {
		this.tpUrl = tpUrl;
	}
	/**
	 * 获取：
	 */
	public String getTpUrl() {
		return tpUrl;
	}
	/**
	 * 设置：
	 */
	public void setTpUser(String tpUser) {
		this.tpUser = tpUser;
	}
	/**
	 * 获取：
	 */
	public String getTpUser() {
		return tpUser;
	}
	/**
	 * 设置：
	 */
	public void setTpPassword(String tpPassword) {
		this.tpPassword = tpPassword;
	}
	/**
	 * 获取：
	 */
	public String getTpPassword() {
		return tpPassword;
	}
	/**
	 * 设置：
	 */
	public void setTpRemark(String tpRemark) {
		this.tpRemark = tpRemark;
	}
	/**
	 * 获取：
	 */
	public String getTpRemark() {
		return tpRemark;
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
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：
	 */
	public Date getCreateTime() {
		return createTime;
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
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：
	 */
	public Date getUpdateTime() {
		return updateTime;
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

	public String getTpTypeName() {
		return tpTypeName;
	}

	public void setTpTypeName(String tpTypeName) {
		this.tpTypeName = tpTypeName;
	}
}
