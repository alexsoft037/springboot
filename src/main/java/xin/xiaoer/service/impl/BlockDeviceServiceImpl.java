package xin.xiaoer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.dao.BlockDeviceDao;
import xin.xiaoer.entity.BlockDevice;
import xin.xiaoer.service.BlockDeviceService;




@Service("blockDeviceService")
@Transactional
public class BlockDeviceServiceImpl implements BlockDeviceService {
	@Autowired
	private BlockDeviceDao blockDeviceDao;
	
	@Override
	public BlockDevice get(Long id){
		return blockDeviceDao.get(id);
	}

	@Override
	public List<BlockDevice> getList(Map<String, Object> map){
		return blockDeviceDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return blockDeviceDao.getCount(map);
	}

	@Override
	public void save(BlockDevice blockDevice){
		blockDeviceDao.save(blockDevice);
	}

	@Override
	public void update(BlockDevice blockDevice){
		blockDeviceDao.update(blockDevice);
	}

	@Override
	public void delete(Long id){
		blockDeviceDao.delete(id);
	}

	@Override
	public void deleteBatch(Long[] ids){
		blockDeviceDao.deleteBatch(ids);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids) {
			BlockDevice blockDevice=get(Long.parseLong(id));
            update(blockDevice);
        }
    }

    @Override
	public 	BlockDevice getByUserAndDevice(Long userId, String deviceId){
		return blockDeviceDao.getByUserAndDevice(userId, deviceId);
	}

	@Override
	public int deleteByUserAndDevice(Long userId, String deviceId){
		return blockDeviceDao.deleteByUserAndDevice(userId, deviceId);
	}
}
