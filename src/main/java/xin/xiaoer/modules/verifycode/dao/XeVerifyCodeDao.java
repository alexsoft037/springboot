package xin.xiaoer.modules.verifycode.dao;

import xin.xiaoer.dao.BaseDao;
import xin.xiaoer.modules.verifycode.entity.XeVerifyCode;

import java.util.Map;

/**
 * 
 * 
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2018-07-17 17:19:51
 */
public interface XeVerifyCodeDao extends BaseDao<XeVerifyCode> {
	XeVerifyCode getVerifyCode (Map<String, Object> map);
}
