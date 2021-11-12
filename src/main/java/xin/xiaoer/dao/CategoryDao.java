package xin.xiaoer.dao;

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
public interface CategoryDao extends BaseDao<Category> {
    List<Category> getListByUpperCode(Map<String, Object> map);

    List<Category> findByVerify(Category category);

    Category getByCategoryCode(String categoryCode);

    int removeUpperCode(@Param("upperCode") String upperCode);

    List<CodeValue> getCodeValues(@Param("upperCode") String upperCode, @Param("state") String state);
}
