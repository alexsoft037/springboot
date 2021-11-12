package xin.xiaoer.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xin.xiaoer.common.enumresource.DefaultEnum;
import xin.xiaoer.common.oss.CloudStorageService;
import xin.xiaoer.common.oss.OSSFactory;
import xin.xiaoer.common.utils.Constant;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.StringUtility;
import xin.xiaoer.dao.SysUserDao;
import xin.xiaoer.entity.Area;
import xin.xiaoer.entity.File;
import xin.xiaoer.entity.SysUser;
import xin.xiaoer.modules.guoshan.entity.GuoshanUser;
import xin.xiaoer.modules.guoshan.service.GuoshanUserService;
import xin.xiaoer.modules.monitor.service.UserChildrenService;
import xin.xiaoer.modules.sysusersns.service.SysUserSnsService;
import xin.xiaoer.modules.website.entity.SysUserIntegralRankItem;
import xin.xiaoer.service.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 系统用户
 *
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2016年9月18日 上午9:46:09
 */
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private UserChildrenService userChildrenService;

    @Autowired
    private SysUserSnsService sysUserSnsService;

    @Autowired
    private FileService fileService;

    @Autowired
    private GuoshanUserService guoshanUserService;

    @Autowired
    private AreaService areaService;

    @Override
    public List<String> queryAllPerms(Long userId) {
        return sysUserDao.queryAllPerms(userId);
    }

    @Override
    public List<Long> queryAllMenuId(Long userId) {
        return sysUserDao.queryAllMenuId(userId);
    }

    @Override
    public SysUser queryByUserName(String username) {
        SysUser sysUser = sysUserDao.queryByUserName(username);
        if (sysUser != null){
            sysUser.setAvatar(getAvatar(sysUser));
            sysUser.setPassword(getPassword(sysUser));
        }
        return sysUser;
    }

    @Override
    public SysUser queryAdminByUserName(String username) {
        SysUser sysUser = sysUserDao.queryAdminByUserName(username);
        if (sysUser != null){
            sysUser.setAvatar(getAvatar(sysUser));
            sysUser.setPassword(getPassword(sysUser));
        }
        return sysUser;
    }

    @Override
    public SysUser queryByPhone(String phoneNo) {
        SysUser sysUser = sysUserDao.queryByPhone(phoneNo);
        if (sysUser != null){
            sysUser.setAvatar(getAvatar(sysUser));
            sysUser.setPassword(getPassword(sysUser));
        }
        return sysUser;
    }

    @Override
    public SysUser queryObject(Long userId) {
        SysUser sysUser = sysUserDao.queryObject(userId);
        if (sysUser != null){
            sysUser.setAvatar(getAvatar(sysUser));
            sysUser.setPassword(getPassword(sysUser));
        }
        return sysUser;
    }

    @Override
    public List<SysUser> queryList(Map<String, Object> map) {
        return sysUserDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return sysUserDao.queryTotal(map);
    }

    @Override
    @Transactional
    public void save(SysUser user) {
        user.setCreateTime(new Date());
        //sha256加密
//        user.setPassword(new Sha256Hash(DefaultEnum.PASSWORD.getCode()).toHex());
        sysUserDao.save(user);

        //检查角色是否越权
        checkRole(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }

    @Override
    @Transactional
    public void insert(SysUser user) {
        user.setCreateTime(new Date());

        sysUserDao.save(user);
    }

    @Override
    @Transactional
    public void update(SysUser user) {
//        if (StringUtils.isBlank(user.getPassword())) {
//            user.setPassword(null);
//        } else {
//            user.setPassword(new Sha256Hash(user.getPassword()).toHex());
//        }
        sysUserDao.update(user);

        //检查角色是否越权
        checkRole(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }

    @Override
    public 	void updateData(SysUser user){
//        if (StringUtils.isBlank(user.getPassword())) {
//            user.setPassword(null);
//        } else {
//            user.setPassword(new Sha256Hash(user.getPassword()).toHex());
//        }
        sysUserDao.update(user);
    }

    @Override
    @Transactional
    public void deleteBatch(Long[] userId) {
        sysUserDao.deleteBatch(userId);
        sysUserDao.deleteUserRole(userId);
        userChildrenService.deleteBatch(userId);
        sysUserSnsService.deleteBatch(userId);
    }

    @Override
    public int updatePassword(Long userId, String password, String newPassword) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("password", password);
        map.put("newPassword", newPassword);
        return sysUserDao.updatePassword(map);
    }

    @Override
    public int updateSession(Map<String, Object> map){
        return sysUserDao.updateSession(map);
    }

    @Override
    public void initPassword(Long[] userIds) {
        for (long userId : userIds) {
            SysUser user = queryObject(userId);
            user.setPassword(new Sha256Hash(DefaultEnum.PASSWORD.getCode()).toHex());
            sysUserDao.update(user);
        }
    }

    /**
     * 检查角色是否越权
     */
    private void checkRole(SysUser user) {
        //如果不是超级管理员，则需要判断用户的角色是否自己创建
        if (user.getCreateUserId() == Constant.SUPER_ADMIN) {
            return;
        }

        //查询用户创建的角色列表
        List<Long> roleIdList = sysRoleService.queryRoleIdList(user.getCreateUserId());

        if (roleIdList == null){
            return;
        }
        //判断是否越权
        if (!roleIdList.containsAll(user.getRoleIdList())) {
//			throw new MyException("新增用户所选角色，不是本人创建");
        }
    }

    public int insertSelective(SysUser member) {
        //检查角色是否越权
        Integer userId = sysUserDao.insertSelective(member);

        checkRole(member);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(member.getUserId(), member.getRoleIdList());
        return userId;
    }

    @Override
    public boolean checkDuplicateUser(SysUser user) {

        Integer rtn;
        try {
            rtn = sysUserDao.checkDuplicateUser(user);
        }catch (Exception e){
            rtn = null;
        }
        return rtn == null ? false : true;
    }

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
            SysUser sysUser=queryObject(Long.parseLong(id));
            sysUser.setStatus(Integer.parseInt(stateValue));
//            update(sysUser);
            sysUserDao.update(sysUser);
        }
    }

    @Override
    public int queryOnlineTotal(){
        return sysUserDao.queryOnlineTotal();
    }

    @Override
    public String getAvatar(SysUser sysUser){
        if (sysUser == null){
            return "";
        }
        UrlValidator urlValidator = new UrlValidator();
        CloudStorageService cloudStorageService = OSSFactory.build();
        if (StringUtility.isNotEmpty(sysUser.getAvatar()) && !urlValidator.isValid(sysUser.getAvatar())) {
            File file = fileService.queryObjectByRelationId(sysUser.getAvatar());
            if (file != null) {
                sysUser.setAvatar(cloudStorageService.generatePresignedUrl(file.getUrl(), 1));
            } else {
                sysUser.setAvatar("");
            }
        }
        return sysUser.getAvatar();
    }

    @Override
    public String getAvatar(String avatar){
        if (avatar == null){
            return null;
        }
        UrlValidator urlValidator = new UrlValidator();
        CloudStorageService cloudStorageService = OSSFactory.build();
        if (StringUtility.isNotEmpty(avatar) && !urlValidator.isValid(avatar)) {
            File file = fileService.queryObjectByRelationId(avatar);
            if (file != null) {
                avatar = cloudStorageService.generatePresignedUrl(file.getUrl(), 1);
            } else {
                avatar = "";
            }
        }
        return avatar;
    }

    private String getPassword(SysUser sysUser) {
        if (sysUser == null){
            return "";
        }
        if (StringUtility.isEmpty(sysUser.getPassword())){
            GuoshanUser guoshanUser = guoshanUserService.get(sysUser.getUserId());
            if (guoshanUser == null){
                return "";
            }
            return guoshanUser.getPassword();
        }

        return sysUser.getPassword();
    }

    @Override
    public String getAddressTxtFromAddressIds(String addressIds){
        StringBuilder builder = new StringBuilder();
        if (StringUtility.isNotEmpty(addressIds)){
            String[] arrayList = addressIds.trim().split(",");
            for (String addressId: arrayList){
                Area area = areaService.queryObject(addressId);
                if (area != null){
                    if (StringUtility.isNotEmpty(area.getAreaName())){
                        builder.append(area.getAreaName() + ", ");
                    }
                }
            }
        }

        String address = builder.toString();
        if (StringUtility.isNotEmpty(address) && address.length() > 3){
            return address.substring(0, address.length() - 2);
        } else {
            return "";
        }
    }

    @Override
    public SysUser getNewLogin(Long userId, String deviceId){
        return sysUserDao.getNewLogin(userId, deviceId);
    }

    @Override
    public List<SysUser> queryUserList(Map<String, Object> map){
        return sysUserDao.queryUserList(map);
    }

    @Override
    public int queryUserTotal(Map<String, Object> map){
        return sysUserDao.queryUserTotal(map);
    }

    @Override
    public List<SysUser> queryAdminList(Map<String, Object> map){
        return sysUserDao.queryAdminList(map);
    }

    @Override
    public int queryAdminTotal(Map<String, Object> map){
        return sysUserDao.queryAdminTotal(map);
    }

    @Override
    public List<SysUser> queryAdminRoleList(Map<String, Object> map){
        return sysUserDao.queryAdminRoleList(map);
    }

    @Override
    public int queryAdminRoleTotal(Map<String, Object> map){
        return sysUserDao.queryAdminRoleTotal(map);
    }

    @Override
    public int getVolunteerCount() {
        return sysUserDao.getVolunteerCount();//志愿者人数
    }

    @Override
    public Integer getNationalRanking(Long userId) {
        return sysUserDao.getNationalRanking(userId);
    }

    @Override
    public List<String> queryBySendTarget(Map<String, Object> params) {
        return sysUserDao.queryBySendTarget(params);
    }

    @Override
    public List<SysUserIntegralRankItem> getUserIntegralRank(Query query) {
        return sysUserDao.getUserIntegralRank(query);
    }

    @Override
    public int getCount(Query query) {
        return sysUserDao.getCount(query);
    }

    @Override
    public SysUser get(Long userId) {
        return sysUserDao.get(userId);
    }

    @Override
    public List<Map<String, String>> getvolunteerRankAtHonorList(Map<String, String> params) {
        return sysUserDao.getvolunteerRankAtHonorList(params);
    }

    @Override
    public List<String> getAccidByRoleId(String s) {
        return sysUserDao.getAccidByRoleId(s);
    }

    @Override
    public String getRemark(Long userId) {
        String s = "";
        List<String> remark = sysUserDao.getRemark(userId);
            for (int i = 0; i < remark.size(); i++) {
                if (i==remark.size()-1){
                    s+=remark.get(i);
                    continue;
                }
                s+=remark.get(i)+",";
            }
            if (StringUtils.isBlank(s)||"null".equals(s)){
                s="无";
            }
        return s;
    }
}
