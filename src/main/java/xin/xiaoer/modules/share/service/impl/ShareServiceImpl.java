package xin.xiaoer.modules.share.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.modules.share.dao.ShareDao;
import xin.xiaoer.modules.share.entity.Share;
import xin.xiaoer.modules.share.service.ShareService;




@Service("shareService")
@Transactional
public class ShareServiceImpl implements ShareService {
	@Autowired
	private ShareDao shareDao;
	
	@Override
	public Share get(Long shareId){
		return shareDao.get(shareId);
	}

	@Override
	public List<Share> getList(Map<String, Object> map){
		return shareDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return shareDao.getCount(map);
	}

	@Override
	public void save(Share share){
		shareDao.save(share);
	}

	@Override
	public void update(Share share){
		shareDao.update(share);
	}

	@Override
	public void delete(Long shareId){
		shareDao.delete(shareId);
	}

	@Override
	public void deleteBatch(Long[] shareIds){
		shareDao.deleteBatch(shareIds);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			Share share=get(Long.parseLong(id));
            update(share);
        }
    }
	
}
