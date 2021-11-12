package xin.xiaoer.modules.monitor.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.modules.monitor.dao.SpaceCameraDao;
import xin.xiaoer.modules.monitor.entity.SpaceCamera;
import xin.xiaoer.modules.monitor.service.SpaceCameraService;




@Service("spaceCameraService")
@Transactional
public class SpaceCameraServiceImpl implements SpaceCameraService {
	@Autowired
	private SpaceCameraDao spaceCameraDao;
	
	@Override
	public SpaceCamera get(Integer id){
		return spaceCameraDao.get(id);
	}

	@Override
	public List<SpaceCamera> getList(Map<String, Object> map){
		return spaceCameraDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return spaceCameraDao.getCount(map);
	}

	@Override
	public void save(SpaceCamera spaceCamera){
		spaceCameraDao.save(spaceCamera);
	}

	@Override
	public void update(SpaceCamera spaceCamera){
		spaceCameraDao.update(spaceCamera);
	}

	@Override
	public void delete(Integer id){
		spaceCameraDao.delete(id);
	}

	@Override
	public void deleteBatch(Integer[] ids){
		spaceCameraDao.deleteBatch(ids);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			SpaceCamera spaceCamera=get(Integer.parseInt(id));
			spaceCamera.setState(stateValue);
            update(spaceCamera);
        }
    }
	
}
