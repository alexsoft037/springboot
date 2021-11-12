package xin.xiaoer.modules.chat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.modules.chat.dao.ChatGroupUserDao;
import xin.xiaoer.modules.chat.entity.ChatGroupUser;
import xin.xiaoer.modules.chat.service.ChatGroupUserService;




@Service("chatGroupUserService")
@Transactional
public class ChatGroupUserServiceImpl implements ChatGroupUserService {
	@Autowired
	private ChatGroupUserDao chatGroupUserDao;
	
	@Override
	public ChatGroupUser get(Long id){
		return chatGroupUserDao.get(id);
	}

	@Override
	public List<ChatGroupUser> getList(Map<String, Object> map){
		return chatGroupUserDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return chatGroupUserDao.getCount(map);
	}

	@Override
	public void save(ChatGroupUser chatGroupUser){
		chatGroupUserDao.save(chatGroupUser);
	}

	@Override
	public void update(ChatGroupUser chatGroupUser){
		chatGroupUserDao.update(chatGroupUser);
	}

	@Override
	public void delete(Long id){
		chatGroupUserDao.delete(id);
	}

	@Override
	public void deleteBatch(Long[] ids){
		chatGroupUserDao.deleteBatch(ids);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			ChatGroupUser chatGroupUser=get(Long.parseLong(id));
//			chatGroupUser.setState(stateValue);
            update(chatGroupUser);
        }
    }

    @Override
	public 	ChatGroupUser getBySpaceAndUser(Long userId, Integer spaceId){
		return chatGroupUserDao.getBySpaceAndUser(userId, spaceId);
	}
}
