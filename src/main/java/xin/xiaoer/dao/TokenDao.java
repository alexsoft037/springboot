package xin.xiaoer.dao;

import xin.xiaoer.entity.Token;

/**
 * 用户Token
 * 
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2017-03-23 15:22:07
 */
public interface TokenDao extends BaseDao<Token> {
    
    Token queryByUserId(String userId);

    Token queryByToken(String token);
	
}
