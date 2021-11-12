package xin.xiaoer.modules.sysusersns.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2018-07-08 17:49:46
 */
public class SysUserSns implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String TYPE_WEIXIN = "SNS003";
	public static final String TYPE_QQ = "SNS002";
	public static final String TYPE_WEIBO = "SNS001";
	/****/
	private Long userId;
	/****/
	private String snsType;
	/****/
	private String accessToken;
	/****/
	private String openid;
	/****/
	private String expiresIn;
	/****/
	private String refreshToken;
	/****/
	private String scope;
	/****/
	private String unionid;
	/****/
	private String oauthConsumerKey;
	/****/
	private String state;
	/****/
	private Date createTime;
	/****/
	private Date updateTime;
	/****/
	private String snsUserId;
	/****/
	private String userName;
	/****/
	private String gender;
	/****/
	private String avatar;

	/**
	 * 设置：
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：
	 */
	public void setSnsType(String snsType) {
		this.snsType = snsType;
	}
	/**
	 * 获取：
	 */
	public String getSnsType() {
		return snsType;
	}
	/**
	 * 设置：
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	/**
	 * 获取：
	 */
	public String getAccessToken() {
		return accessToken;
	}
	/**
	 * 设置：
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	/**
	 * 获取：
	 */
	public String getOpenid() {
		return openid;
	}
	/**
	 * 设置：
	 */
	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}
	/**
	 * 获取：
	 */
	public String getExpiresIn() {
		return expiresIn;
	}
	/**
	 * 设置：
	 */
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	/**
	 * 获取：
	 */
	public String getRefreshToken() {
		return refreshToken;
	}
	/**
	 * 设置：
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}
	/**
	 * 获取：
	 */
	public String getScope() {
		return scope;
	}
	/**
	 * 设置：
	 */
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	/**
	 * 获取：
	 */
	public String getUnionid() {
		return unionid;
	}
	/**
	 * 设置：
	 */
	public void setOauthConsumerKey(String oauthConsumerKey) {
		this.oauthConsumerKey = oauthConsumerKey;
	}
	/**
	 * 获取：
	 */
	public String getOauthConsumerKey() {
		return oauthConsumerKey;
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
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	public String getSnsUserId() {
		return snsUserId;
	}

	public void setSnsUserId(String snsUserId) {
		this.snsUserId = snsUserId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
}
