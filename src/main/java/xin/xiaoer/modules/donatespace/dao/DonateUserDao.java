package xin.xiaoer.modules.donatespace.dao;

import org.apache.ibatis.annotations.Param;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.dao.BaseDao;
import xin.xiaoer.modules.donatespace.entity.DonateUser;
import xin.xiaoer.modules.donatespace.entity.DonateUserDetail;
import xin.xiaoer.modules.mobile.entity.DonateUserResume;
import xin.xiaoer.modules.website.entity.WebAccount;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2018-07-20 00:22:00
 */
public interface DonateUserDao extends BaseDao<DonateUser> {

    DonateUserResume getDonateResumeByItemId(Long itemId);

    List<DonateUser> getListGroupByItemId(Map<String, Object> params);

    Double getTotalDonateAmount(@Param("userId") Long userId, @Param("spaceId") String spaceId);

    int getTotalItemCount(@Param("userId") Long userId, @Param("spaceId") String spaceId);

    DonateUserDetail getDetail(Long id);

    Integer getDonateCount();

    int getAttendDonateCount();

    String getSumAmount(@Param("userId") Long userId);

    List<WebAccount> getListAccountByUserId(Query query);
}
