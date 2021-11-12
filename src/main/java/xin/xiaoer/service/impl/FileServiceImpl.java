package xin.xiaoer.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xin.xiaoer.common.oss.OSSFactory;
import xin.xiaoer.common.utils.StringUtility;
import xin.xiaoer.dao.FileDao;
import xin.xiaoer.entity.File;
import xin.xiaoer.service.FileService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("fileService")
@Transactional
public class FileServiceImpl implements FileService {
	@Autowired
	private FileDao fileDao;
	
	@Override
	public File get(String id){
		return fileDao.get(id);
	}

	@Override
	public List<File> getList(Map<String, Object> map){
		return fileDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return fileDao.getCount(map);
	}

	@Override
	public void save(File file){
		fileDao.save(file);
	}

	@Override
	public void update(File file){
		fileDao.update(file);
	}

	@Override
	public void delete(String id){
		File file = fileDao.get(id);
//		file.deleteFile();
		OSSFactory.build().deleteObject(file.getUrl());
		fileDao.delete(id);
	}

	@Override
	public void deleteBatch(String[] ids){
		List<String> keys = new ArrayList<>();
		for (String id: ids){
			File file = fileDao.get(id);
//			file.deleteFile();
			if (StringUtility.isNotEmpty(file.getUrl())){
				keys.add(file.getUrl());
			}
		}
		OSSFactory.build().deleteObjects(keys);
		fileDao.deleteBatch(ids);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			File file=get(id);
			//file.setState(stateValue);
            update(file);
        }
    }

	@Override
	public List<File> getByRelationId(String relationId) {
		return fileDao.getByRelationId(relationId);
	}

	@Override
	public List<File> getFileList(String imgUUID) {
		return fileDao.getFileList(imgUUID);
	}

	@Override
	public List<File> getFileListByUUID(List<String> uuidList) {
		return fileDao.getFileListByUUID(uuidList);
	}

	@Override
	public void deleteByRelationId(String relationId) {
		List<File> fileList = fileDao.getByRelationId(relationId);
		List<String> keys = new ArrayList<>();
		for (File file: fileList){
			if (StringUtility.isNotEmpty(file.getUrl())){
				keys.add(file.getUrl());
			}
//			file.deleteFile();
		}
		OSSFactory.build().deleteObjects(keys);
		fileDao.deleteByRelationId(relationId);
	}

	@Override
	public File queryObjectByRelationId(String relationId){
		return fileDao.queryObjectByRelationId(relationId);
	}
}
