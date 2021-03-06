package xin.xiaoer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.xiaoer.dao.TokenDao;
import xin.xiaoer.entity.Token;
import xin.xiaoer.service.TokenService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Service("tokenService")
public class TokenServiceImpl implements TokenService {
	@Autowired
	private TokenDao tokenDao;
	//12小时后过期
	private final static int EXPIRE = 3600 * 12;

	@Override
	public Token queryByUserId(String userId) {
		return tokenDao.queryByUserId(userId);
	}

	@Override
	public Token queryByToken(String token) {
		return tokenDao.queryByToken(token);
	}

	@Override
	public void save(Token token){
		tokenDao.save(token);
	}
	
	@Override
	public void update(Token token){
		tokenDao.update(token);
	}

	@Override
	public Map<String, Object> createToken(String userId) {
		//生成一个token
		String token = UUID.randomUUID().toString();

		//添加
		token=token.replace("-", "");

		//当前时间
		Date now = new Date();

		//过期时间
		Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

		//判断是否生成过token
		Token tokenEntity = queryByUserId(userId);
		if(tokenEntity == null){
			tokenEntity = new Token();
			tokenEntity.setUserId(userId);
			tokenEntity.setToken(token);
			tokenEntity.setUpdateTime(now);
			tokenEntity.setExpireTime(expireTime);

			//保存token
			save(tokenEntity);
		}else{
			tokenEntity.setToken(token);
			tokenEntity.setUpdateTime(now);
			tokenEntity.setExpireTime(expireTime);

			//更新token
			update(tokenEntity);
		}

		Map<String, Object> map = new HashMap<>();
		map.put("token", token);
		map.put("expire", EXPIRE);

		return map;
	}
}
