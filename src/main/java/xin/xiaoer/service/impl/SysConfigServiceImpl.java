package xin.xiaoer.service.impl;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xin.xiaoer.common.enumresource.StateEnum;
import xin.xiaoer.common.exception.MyException;
import xin.xiaoer.common.utils.DateUtil;
import xin.xiaoer.common.utils.RRException;
import xin.xiaoer.dao.SysConfigDao;
import xin.xiaoer.dao.SysConfigEntityDao;
import xin.xiaoer.dao.SysOssDao;
import xin.xiaoer.entity.SysConfig;
import xin.xiaoer.entity.SysConfigEntity;
import xin.xiaoer.entity.SysOss;
import xin.xiaoer.redis.SysConfigRedis;
import xin.xiaoer.service.SysConfigService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("sysConfigService")
public class SysConfigServiceImpl extends ServiceImpl<SysConfigEntityDao, SysConfigEntity> implements SysConfigService {
	@Autowired
	private SysConfigDao sysConfigDao;

	@Autowired
	private SysOssDao sysOssDao;

	@Autowired
	private SysConfigRedis sysConfigRedis;

	@Override
	public void save(SysConfig config) {
		sysConfigDao.save(config);
	}

	@Override
	public void update(SysConfig config) {
		sysConfigDao.update(config);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateValueByKey(String key, String value) {
		sysConfigDao.updateValueByKey(key, value);
	}

	@Override
	public void deleteBatch(Long[] ids) {
		sysConfigDao.deleteBatch(ids);
	}

	@Override
	public List<SysConfig> queryList(Map<String, Object> map) {
		return sysConfigDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return sysConfigDao.queryTotal(map);
	}

	@Override
	public SysConfig queryObject(Long id) {
		return sysConfigDao.queryObject(id);
	}

	@Override
	public String getValue(String key, String defaultValue) {
		String value = sysConfigDao.queryByKey(key);
		if(StringUtils.isBlank(value)) {
			return defaultValue;
		}
		return value;
	}

	@Override
	public String getValueByKey(String key) {
		SysConfig config = sysConfigDao.getByCode(key);
		if(config == null){
			config = new SysConfig();
			config.setCode(key);
			sysConfigDao.save(config);
		}

		return config.getValue();
	}

	@Override
	public <T> T getConfigObject(String key, Class<T> clazz) {
		Map<String, Object> params = new HashMap<>();
		params.put("state", StateEnum.ENABLE.getCode());
		List<SysOss> ossList = sysOssDao.getList(params);
		if (ossList != null && ossList.size() > 0) {
			SysOss oss = ossList.get(0);
			String endpoint = oss.getEndpoint();
			String accessKeyId = oss.getAccessKeyId();
			String accessKeySecret = oss.getAccessKeySecret();
			String bucket = oss.getBucket();
			String domain = oss.getUrl();
			Map<String, Object> ossConfig = new HashMap<>();
			ossConfig.put("type", 2);
			ossConfig.put("aliyunAccessKeyId", accessKeyId);
			ossConfig.put("aliyunAccessKeySecret", accessKeySecret);
			ossConfig.put("aliyunBucketName", bucket);
			ossConfig.put("aliyunDomain", domain);
			ossConfig.put("aliyunEndPoint", endpoint);
			ossConfig.put("aliyunPrefix", "upload");

			Gson gson = new Gson();
			JsonElement jsonElement = gson.toJsonTree(ossConfig);
			return gson.fromJson(jsonElement, clazz);
		}
//		String value = getValue(key, null);
//		if(StringUtils.isNotBlank(value)){
//			return JSON.parseObject(value, clazz);
//		}

		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new MyException("获取参数失败");
		}
	}

	@Override
	public <T> T getConfigClassObject(String key, Class<T> clazz){
		String value = getValueByKey(key);
		if(StringUtils.isNotBlank(value)){
			return new Gson().fromJson(value, clazz);
		}

		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new RRException("获取参数失败");
		}
	}

	@Override
	public List<SysConfig> findRule(Map<String, Object> params) {
		return sysConfigDao.findRule(params);
	}

	@Override
	public void setRule(SysConfig config) {
		sysConfigDao.setRule(config);
	}

	@Override
	public List<SysConfig> findByCode(String code) {
		return sysConfigDao.findByCode(code);
	}

	@Override
	public SysConfig getByCode(String code){
		return sysConfigDao.getByCode(code);
	}
}
