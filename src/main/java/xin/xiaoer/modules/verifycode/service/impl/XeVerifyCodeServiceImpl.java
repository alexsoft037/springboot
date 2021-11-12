package xin.xiaoer.modules.verifycode.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xin.xiaoer.modules.verifycode.dao.XeVerifyCodeDao;
import xin.xiaoer.modules.verifycode.entity.XeVerifyCode;
import xin.xiaoer.modules.verifycode.service.XeVerifyCodeService;




@Service("xeVerifyCodeService")
@Transactional
public class XeVerifyCodeServiceImpl implements XeVerifyCodeService {
	@Autowired
	private XeVerifyCodeDao xeVerifyCodeDao;
	
	@Override
	public XeVerifyCode get(Long id){
		return xeVerifyCodeDao.get(id);
	}

	@Override
	public List<XeVerifyCode> getList(Map<String, Object> map){
		return xeVerifyCodeDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return xeVerifyCodeDao.getCount(map);
	}

	@Override
	public void save(XeVerifyCode xeVerifyCode){
		xeVerifyCodeDao.save(xeVerifyCode);
	}

	@Override
	public void update(XeVerifyCode xeVerifyCode){
		xeVerifyCodeDao.update(xeVerifyCode);
	}

	@Override
	public void delete(Long id){
		xeVerifyCodeDao.delete(id);
	}

	@Override
	public void deleteBatch(Long[] ids){
		xeVerifyCodeDao.deleteBatch(ids);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			XeVerifyCode xeVerifyCode=get(Long.parseLong(id));
			xeVerifyCode.setState(stateValue);
            update(xeVerifyCode);
        }
    }

	@Override
	public XeVerifyCode getVerifyCode (String phone, String typeCode){
		Map<String, Object> map = new HashMap<>();
		map.put("phone", phone);
		map.put("verifyTypeCode", typeCode);
		return xeVerifyCodeDao.getVerifyCode(map);
	}

	@Override
	public boolean checkDuplicateVerifyCode(XeVerifyCode xeVerifyCode){
		XeVerifyCode xeVerifyCode1 = this.getVerifyCode(xeVerifyCode.getPhone(), xeVerifyCode.getVerifyTypeCode());
		return xeVerifyCode1 == null ? false : true;
	}
}
