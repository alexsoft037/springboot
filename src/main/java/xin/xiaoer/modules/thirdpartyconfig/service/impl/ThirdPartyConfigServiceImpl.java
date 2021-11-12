package xin.xiaoer.modules.thirdpartyconfig.service.impl;

import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.modules.thirdpartyconfig.dao.ThirdPartyConfigDao;
import xin.xiaoer.modules.thirdpartyconfig.entity.ThirdPartyConfig;
import xin.xiaoer.modules.thirdpartyconfig.service.ThirdPartyConfigService;




@Service("thirdPartyConfigService")
@Transactional
public class ThirdPartyConfigServiceImpl implements ThirdPartyConfigService {
	@Autowired
	private ThirdPartyConfigDao thirdPartyConfigDao;
	
	@Override
	public ThirdPartyConfig get(Integer id){
		return thirdPartyConfigDao.get(id);
	}

	@Override
	public List<ThirdPartyConfig> getList(Map<String, Object> map){
		return thirdPartyConfigDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return thirdPartyConfigDao.getCount(map);
	}

	@Override
	public void save(ThirdPartyConfig thirdPartyConfig){
		thirdPartyConfigDao.save(thirdPartyConfig);
	}

	@Override
	public void update(ThirdPartyConfig thirdPartyConfig){
		thirdPartyConfigDao.update(thirdPartyConfig);
	}

	@Override
	public void delete(Integer id){
		thirdPartyConfigDao.delete(id);
	}

	@Override
	public void deleteBatch(Integer[] ids){
		thirdPartyConfigDao.deleteBatch(ids);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			ThirdPartyConfig thirdPartyConfig=get(Integer.parseInt(id));
			thirdPartyConfig.setState(stateValue);
            update(thirdPartyConfig);
        }
    }
	
}
