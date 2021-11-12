package xin.xiaoer.service;

import xin.xiaoer.entity.Token;

import java.util.Map;

/**
 * 用户Token
 * 
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2017-03-23 15:22:07
 */
public interface TokenService {

	Token queryByUserId(String userId);

	Token queryByToken(String token);
	
	void save(Token token);
	
	void update(Token token);

	/**
	 * 生成token
	 * @param userId  用户ID
	 * @return        返回token相关信息
	 */
	Map<String, Object> createToken(String userId);

}
