package xin.xiaoer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.dao.CategoryDao;
import xin.xiaoer.entity.Category;
import xin.xiaoer.entity.CodeValue;
import xin.xiaoer.service.CategoryService;

@Service("categoryService")
@Transactional
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryDao categoryDao;
	
	@Override
	public Category get(Integer categoryId){
		return categoryDao.get(categoryId);
	}

	@Override
	public List<Category> getList(Map<String, Object> map){
		return categoryDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return categoryDao.getCount(map);
	}

	@Override
	public void save(Category category){
		categoryDao.save(category);
	}

	@Override
	public void update(Category category){
		categoryDao.update(category);
	}

	@Override
	public void delete(Integer categoryId){
		categoryDao.delete(categoryId);
	}

	@Override
	public void deleteBatch(Integer[] categoryIds){
		categoryDao.deleteBatch(categoryIds);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			Category category =get(Integer.parseInt(id));
			category.setState(stateValue);
            update(category);
        }
    }

	@Override
	public Category getByCategoryCode(String categoryCode){
		return categoryDao.getByCategoryCode(categoryCode);
	}

	@Override
	public List<Category> getListByUpperCode(Map<String, Object> map){
		return categoryDao.getListByUpperCode(map);
	}

	@Override
	public int removeUpperCode(String upperCode){
		return categoryDao.removeUpperCode(upperCode);
	}

	@Override
	public List<CodeValue> getCodeValues(String upperCode){
		return categoryDao.getCodeValues(upperCode, null);
	}

	@Override
	public List<CodeValue> getCodeValues(String upperCode, String state) {
		return categoryDao.getCodeValues(upperCode, state);
	}

	@Override
	public List<Category> findByVerify(Category category){
		return categoryDao.findByVerify(category);
	}
}
