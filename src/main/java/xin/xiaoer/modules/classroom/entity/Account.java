package xin.xiaoer.modules.classroom.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-09-18 19:00:52
 */
public class Account implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**用户名**/
	private String uid;
	/**用户密码**/
	private String pwd;
	/**用户token**/
	private String token;
	/**登录状态**/
	private Integer state;
	/**sig**/
	private String userSig;
	/**注册时间戳**/
	private Integer registerTime;
	/**登录时间戳**/
	private Integer loginTime;
	/**退出时间戳**/
	private Integer logoutTime;
	/**最新请求时间戳**/
	private Integer lastRequestTime;
	/**当前appid**/
	private Integer currentAppid;

	/**
	 * 设置：用户名
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}
	/**
	 * 获取：用户名
	 */
	public String getUid() {
		return uid;
	}
	/**
	 * 设置：用户密码
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	/**
	 * 获取：用户密码
	 */
	public String getPwd() {
		return pwd;
	}
	/**
	 * 设置：用户token
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * 获取：用户token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * 设置：登录状态
	 */
	public void setState(Integer state) {
		this.state = state;
	}
	/**
	 * 获取：登录状态
	 */
	public Integer getState() {
		return state;
	}
	/**
	 * 设置：sig
	 */
	public void setUserSig(String userSig) {
		this.userSig = userSig;
	}
	/**
	 * 获取：sig
	 */
	public String getUserSig() {
		return userSig;
	}
	/**
	 * 设置：注册时间戳
	 */
	public void setRegisterTime(Integer registerTime) {
		this.registerTime = registerTime;
	}
	/**
	 * 获取：注册时间戳
	 */
	public Integer getRegisterTime() {
		return registerTime;
	}
	/**
	 * 设置：登录时间戳
	 */
	public void setLoginTime(Integer loginTime) {
		this.loginTime = loginTime;
	}
	/**
	 * 获取：登录时间戳
	 */
	public Integer getLoginTime() {
		return loginTime;
	}
	/**
	 * 设置：退出时间戳
	 */
	public void setLogoutTime(Integer logoutTime) {
		this.logoutTime = logoutTime;
	}
	/**
	 * 获取：退出时间戳
	 */
	public Integer getLogoutTime() {
		return logoutTime;
	}
	/**
	 * 设置：最新请求时间戳
	 */
	public void setLastRequestTime(Integer lastRequestTime) {
		this.lastRequestTime = lastRequestTime;
	}
	/**
	 * 获取：最新请求时间戳
	 */
	public Integer getLastRequestTime() {
		return lastRequestTime;
	}
	/**
	 * 设置：当前appid
	 */
	public void setCurrentAppid(Integer currentAppid) {
		this.currentAppid = currentAppid;
	}
	/**
	 * 获取：当前appid
	 */
	public Integer getCurrentAppid() {
		return currentAppid;
	}
}
