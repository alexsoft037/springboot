package xin.xiaoer.modules.advert.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.modules.advert.dao.XeAdvertDao;
import xin.xiaoer.modules.advert.entity.XeAdvert;
import xin.xiaoer.modules.advert.service.XeAdvertService;




@Service("xeAdvertService")
@Transactional
public class XeAdvertServiceImpl implements XeAdvertService {
	@Autowired
	private XeAdvertDao xeAdvertDao;
	
	@Override
	public XeAdvert get(Integer id){
		return xeAdvertDao.get(id);
	}

	@Override
	public List<XeAdvert> getList(Map<String, Object> map){
		return xeAdvertDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return xeAdvertDao.getCount(map);
	}

	@Override
	public void save(XeAdvert xeAdvert){
		xeAdvertDao.save(xeAdvert);
	}

	@Override
	public void update(XeAdvert xeAdvert){
		xeAdvertDao.update(xeAdvert);
	}

	@Override
	public void delete(Integer id){
		xeAdvertDao.delete(id);
	}

	@Override
	public void deleteBatch(Integer[] ids){
		xeAdvertDao.deleteBatch(ids);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			XeAdvert xeAdvert=get(Integer.parseInt(id));
			xeAdvert.setState(stateValue);
            update(xeAdvert);
        }
    }

	@Override
	public int removeArticle(String articleType, Long articleId){
		return xeAdvertDao.removeArticle(articleType, articleId);
	}
}
