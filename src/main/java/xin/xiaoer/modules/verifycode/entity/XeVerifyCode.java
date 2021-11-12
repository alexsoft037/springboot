package xin.xiaoer.modules.verifycode.entity;

import xin.xiaoer.common.utils.DateUtility;
import xin.xiaoer.common.utils.StringUtil;
import xin.xiaoer.common.utils.StringUtility;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2018-07-17 17:19:51
 */
public class XeVerifyCode implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Long validationTime = 2*60*60*1000L;

	public static final String REGISTER = "VT0001";
	public static final String FORGET_PASSWORD = "VT0002";

	/****/
	private Long id;
	/****/
	private String phone;
	/****/
	private String code;
	/****/
	private String verifyTypeCode;
	/****/
	private String state;
	/****/
	private Date createAt;

	/**
	 * 设置：
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取：
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 设置：
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取：
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置：
	 */
	public void setVerifyTypeCode(String verifyTypeCode) {
		this.verifyTypeCode = verifyTypeCode;
	}
	/**
	 * 获取：
	 */
	public String getVerifyTypeCode() {
		return verifyTypeCode;
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

	public boolean checkValidation(){
		long difference = DateUtility.getCurrentTime().getTime() - createAt.getTime();
		if (difference <= validationTime){
			return true;
		} else {
			return false;
		}
	}
}
