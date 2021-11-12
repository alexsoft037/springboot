package xin.xiaoer.service;

import org.apache.ibatis.annotations.Param;
import xin.xiaoer.entity.Category;
import xin.xiaoer.entity.CodeValue;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-07-30 11:10:06
 */
public interface CategoryService {
	
	Category get(Integer categoryId);
	
	List<Category> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(Category category);
	
	void update(Category category);
	
	void delete(Integer categoryId);
	
	void deleteBatch(Integer[] categoryIds);

    void updateState(String[] ids, String stateValue);

	Category getByCategoryCode(String categoryCode);

	List<Category> getListByUpperCode(Map<String, Object> map);

	int removeUpperCode(String upperCode);

	List<Category> findByVerify(Category category);

	List<CodeValue> getCodeValues(String upperCode);

	List<CodeValue> getCodeValues(String upperCode, String state);
}
