package xin.xiaoer.modules.verifycode.service;

import xin.xiaoer.modules.verifycode.entity.XeVerifyCode;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2018-07-17 17:19:51
 */
public interface XeVerifyCodeService {
	
	XeVerifyCode get(Long id);
	
	List<XeVerifyCode> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(XeVerifyCode xeVerifyCode);
	
	void update(XeVerifyCode xeVerifyCode);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

    void updateState(String[] ids, String stateValue);

	XeVerifyCode getVerifyCode (String phone, String typeCode);

	boolean checkDuplicateVerifyCode(XeVerifyCode xeVerifyCode);
}
