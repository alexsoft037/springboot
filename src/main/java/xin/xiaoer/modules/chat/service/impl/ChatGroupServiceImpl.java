package xin.xiaoer.modules.chat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.modules.chat.dao.ChatGroupDao;
import xin.xiaoer.modules.chat.entity.ChatGroup;
import xin.xiaoer.modules.chat.service.ChatGroupService;




@Service("chatGroupService")
@Transactional
public class ChatGroupServiceImpl implements ChatGroupService {
	@Autowired
	private ChatGroupDao chatGroupDao;
	
	@Override
	public ChatGroup get(Integer spaceId){
		return chatGroupDao.get(spaceId);
	}

	@Override
	public List<ChatGroup> getList(Map<String, Object> map){
		return chatGroupDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return chatGroupDao.getCount(map);
	}

	@Override
	public void save(ChatGroup chatGroup){
		chatGroupDao.save(chatGroup);
	}

	@Override
	public void update(ChatGroup chatGroup){
		chatGroupDao.update(chatGroup);
	}

	@Override
	public void delete(Integer spaceId){
		chatGroupDao.delete(spaceId);
	}

	@Override
	public void deleteBatch(Integer[] spaceIds){
		chatGroupDao.deleteBatch(spaceIds);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			ChatGroup chatGroup=get(Integer.parseInt(id));
			chatGroup.setState(stateValue);
            update(chatGroup);
        }
    }

    @Override
	public List<ChatGroup> getListByUser(Long userId){
		return chatGroupDao.getListByUser(userId);
	}

	@Override
	public	List<ChatGroup> getAdminList(Map<String, Object> map){
		return chatGroupDao.getAdminList(map);
	}

	@Override
	public int getAdminCount(Map<String, Object> map){
		return chatGroupDao.getAdminCount(map);
	}
}
