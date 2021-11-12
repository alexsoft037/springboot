package xin.xiaoer.modules.space.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.entity.CodeValue;
import xin.xiaoer.modules.space.dao.SpaceDao;
import xin.xiaoer.modules.space.entity.Space;
import xin.xiaoer.modules.space.service.SpaceService;
import xin.xiaoer.modules.website.entity.SpaceRankItem;

import java.util.List;
import java.util.Map;


@Service("spaceService")
@Transactional
public class SpaceServiceImpl implements SpaceService {
	@Autowired
	private SpaceDao spaceDao;
	
	@Override
	public Space get(Integer spaceId){
		return spaceDao.get(spaceId);
	}

	@Override
	public List<Space> getList(Map<String, Object> map){
		return spaceDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return spaceDao.getCount(map);
	}

	@Override
	public void save(Space space){
		spaceDao.save(space);
	}

	@Override
	public void update(Space space){
		spaceDao.update(space);
	}

	@Override
	public void delete(Integer spaceId){
		spaceDao.delete(spaceId);
	}

	@Override
	public void deleteBatch(Integer[] spaceIds){
		spaceDao.deleteBatch(spaceIds);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			Space space=get(Integer.parseInt(id));
			space.setState(stateValue);
            update(space);
        }
    }

    @Override
	public List<Space> getSpaceList(Map<String, Object> map){
		return spaceDao.getSpaceList(map);
	}

	@Override
	public Space getByAddress(Map<String, Object> map) {

		Space space = new Space();
		List<Space> spaceList = spaceDao.getByAddress(map);
		if (spaceList == null || spaceList.size() < 1){
			map.remove("streetNumber");
			spaceList = spaceDao.getByAddress(map);
		}
		if (spaceList == null || spaceList.size() < 1){
			map.remove("street");
			spaceList = spaceDao.getByAddress(map);
		}
		if (spaceList == null || spaceList.size() < 1){
			map.remove("district");
			spaceList = spaceDao.getByAddress(map);
		}
		if (spaceList == null || spaceList.size() < 1){
			map.remove("city");
			spaceList = spaceDao.getByAddress(map);
		}
		if (spaceList != null && spaceList.size() > 0){
			space = spaceList.get(0);
		}

		return space;
	}

	@Override
	public List<CodeValue> getCodeValues(Map<String, Object> map){
		return spaceDao.getCodeValues(map);
	}

	@Override
	public String getByCity(String city) {
		return spaceDao.getByCity(city);
	}

	@Override
	public List<SpaceRankItem> getSpaceRank(Query query) {
		return spaceDao.getSpaceRank(query);
	}

	@Override
	public List<Map<String,String>> getSpaceRankAtHonorList(Map<String, String> params) {
		return spaceDao.getSpaceRankAtHonorList(params);
	}
}
