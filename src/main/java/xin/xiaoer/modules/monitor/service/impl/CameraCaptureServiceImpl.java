package xin.xiaoer.modules.monitor.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.modules.monitor.dao.CameraCaptureDao;
import xin.xiaoer.modules.monitor.entity.CameraCapture;
import xin.xiaoer.modules.monitor.service.CameraCaptureService;




@Service("cameraCaptureService")
@Transactional
public class CameraCaptureServiceImpl implements CameraCaptureService {
	@Autowired
	private CameraCaptureDao cameraCaptureDao;
	
	@Override
	public CameraCapture get(Long id){
		return cameraCaptureDao.get(id);
	}

	@Override
	public List<CameraCapture> getList(Map<String, Object> map){
		return cameraCaptureDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return cameraCaptureDao.getCount(map);
	}

	@Override
	public void save(CameraCapture cameraCapture){
		cameraCaptureDao.save(cameraCapture);
	}

	@Override
	public void update(CameraCapture cameraCapture){
		cameraCaptureDao.update(cameraCapture);
	}

	@Override
	public void delete(Long id){
		cameraCaptureDao.delete(id);
	}

	@Override
	public void deleteBatch(Long[] ids){
		cameraCaptureDao.deleteBatch(ids);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			CameraCapture cameraCapture=get(Long.parseLong(id));
//			cameraCapture.setState(stateValue);
            update(cameraCapture);
        }
    }

    @Override
	public List<String> getDateList(Map<String, Object> map){
		return cameraCaptureDao.getDateList(map);
	}

	@Override
	public List<CameraCapture> getCaptureList(Map<String, Object> map){
		return cameraCaptureDao.getCaptureList(map);
	}
}
