package xin.xiaoer.modules.html5.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.modules.html5.dao.HtmlFileDao;
import xin.xiaoer.modules.html5.entity.HtmlFile;
import xin.xiaoer.modules.html5.service.HtmlFileService;




@Service("htmlFileService")
@Transactional
public class HtmlFileServiceImpl implements HtmlFileService {
	@Autowired
	private HtmlFileDao htmlFileDao;
	
	@Override
	public HtmlFile get(Integer id){
		return htmlFileDao.get(id);
	}

	@Override
	public List<HtmlFile> getList(Map<String, Object> map){
		return htmlFileDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return htmlFileDao.getCount(map);
	}

	@Override
	public void save(HtmlFile htmlFile){
		htmlFileDao.save(htmlFile);
	}

	@Override
	public void update(HtmlFile htmlFile){
		htmlFileDao.update(htmlFile);
	}

	@Override
	public void delete(Integer id){
		htmlFileDao.delete(id);
	}

	@Override
	public void deleteBatch(Integer[] ids){
		htmlFileDao.deleteBatch(ids);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			HtmlFile htmlFile=get(Integer.parseInt(id));
			htmlFile.setState(stateValue);
            update(htmlFile);
        }
    }

    @Override
	public List<HtmlFile> getCodeValues(Map<String, Object> map){
		return htmlFileDao.getCodeValues(map);
	}
}
