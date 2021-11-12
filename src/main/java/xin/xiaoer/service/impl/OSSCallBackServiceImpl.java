package xin.xiaoer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xin.xiaoer.dao.OSSCallBackDao;
import xin.xiaoer.service.OSSCallBackService;

import java.util.Map;

/**
 * @ClassName OSSCallBackServiceImpl
 * @Description TODO
 * @Author XiaoDong
 **/
@Service("OSSCallBackService")
@Transactional
public class OSSCallBackServiceImpl implements OSSCallBackService {
    @Autowired
    private OSSCallBackDao ossCallBackDao;
    @Override
    public void save(Map<String, Object> params) {
        ossCallBackDao.save(params);
    }
}
