package xin.xiaoer.modules.xebanner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xin.xiaoer.modules.xebanner.dao.XeBannerDao;
import xin.xiaoer.modules.xebanner.entity.XeBanner;
import xin.xiaoer.modules.xebanner.entity.XeBannerStory;
import xin.xiaoer.modules.xebanner.service.XeBannerService;

import java.util.List;
import java.util.Map;




@Service("xeBannerService")
@Transactional
public class XeBannerServiceImpl implements XeBannerService {
	@Autowired
	private XeBannerDao xeBannerDao;
	
	@Override
	public XeBanner get(Integer id){
		return xeBannerDao.get(id);
	}

	@Override
	public List<XeBanner> getList(Map<String, Object> map){
		return xeBannerDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return xeBannerDao.getCount(map);
	}

	@Override
	public void save(XeBanner xeBanner){
		xeBannerDao.save(xeBanner);
	}

	@Override
	public void update(XeBanner xeBanner){
		xeBannerDao.update(xeBanner);
	}

	@Override
	public void delete(Integer id){
		xeBannerDao.delete(id);
	}

	@Override
	public void deleteBatch(Integer[] ids){
		xeBannerDao.deleteBatch(ids);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			XeBanner xeBanner=get(Integer.parseInt(id));
			xeBanner.setState(stateValue);
            update(xeBanner);
        }
    }

    @Override
	public int removeArticle(String articleType, Long articleId){
		return xeBannerDao.removeArticle(articleType, articleId);
	}

	@Override
	public List<XeBannerStory> getListStory() {
		return xeBannerDao.getListStory();
	}
}
