package xin.xiaoer.modules.chat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xin.xiaoer.modules.chat.dao.ChatUserDao;
import xin.xiaoer.modules.chat.entity.ChatUser;
import xin.xiaoer.modules.chat.service.ChatUserService;

import java.util.List;
import java.util.Map;




@Service("chatUserService")
@Transactional
public class ChatUserServiceImpl implements ChatUserService {
	@Autowired
	private ChatUserDao chatUserDao;
	
	@Override
	public ChatUser get(Long userId){
		return chatUserDao.get(userId);
	}

	@Override
	public List<ChatUser> getList(Map<String, Object> map){
		return chatUserDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return chatUserDao.getCount(map);
	}

	@Override
	public void save(ChatUser chatUser){
		chatUserDao.save(chatUser);
	}

	@Override
	public void update(ChatUser chatUser){
		chatUserDao.update(chatUser);
	}

	@Override
	public void delete(Long userId){
		chatUserDao.delete(userId);
	}

	@Override
	public void deleteBatch(Long[] userIds){
		chatUserDao.deleteBatch(userIds);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			ChatUser chatUser=get(Long.parseLong(id));
			chatUser.setState(stateValue);
            update(chatUser);
        }
    }

    @Override
	public List<ChatUser> getGroupUserList(Integer spaceId){
		return chatUserDao.getGroupUserList(spaceId);
	}

	@Override
	public List<Long> queryByAccids(List<String> accids) {
		return chatUserDao.queryByAccids(accids);
	}
}
