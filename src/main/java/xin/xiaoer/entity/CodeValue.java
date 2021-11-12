package xin.xiaoer.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import xin.xiaoer.common.utils.Const;
import xin.xiaoer.common.utils.PathUtil;

import java.io.Serializable;
import java.util.Date;


/**
 * 地产附件表
 * 
 * @author chenyi
 * @email qq228112142@qq.com
 * @date 2017-11-17 11:52:01
 */
public class CodeValue implements Serializable {
	private static final long serialVersionUID = 1L;

	private String code;

	private String value;

	private String icon;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}
