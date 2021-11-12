package xin.xiaoer.modules.monitor.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.modules.monitor.dao.CameraRecordDao;
import xin.xiaoer.modules.monitor.entity.CameraRecord;
import xin.xiaoer.modules.monitor.service.CameraRecordService;




@Service("cameraRecordService")
@Transactional
public class CameraRecordServiceImpl implements CameraRecordService {
	@Autowired
	private CameraRecordDao cameraRecordDao;
	
	@Override
	public CameraRecord get(Long id){
		return cameraRecordDao.get(id);
	}

	@Override
	public List<CameraRecord> getList(Map<String, Object> map){
		return cameraRecordDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return cameraRecordDao.getCount(map);
	}

	@Override
	public void save(CameraRecord cameraRecord){
		cameraRecordDao.save(cameraRecord);
	}

	@Override
	public void update(CameraRecord cameraRecord){
		cameraRecordDao.update(cameraRecord);
	}

	@Override
	public void delete(Long id){
		cameraRecordDao.delete(id);
	}

	@Override
	public void deleteBatch(Long[] ids){
		cameraRecordDao.deleteBatch(ids);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			CameraRecord cameraRecord=get(Long.parseLong(id));
//			cameraRecord.setState(stateValue);
            update(cameraRecord);
        }
    }
	
}
