package xin.xiaoer.modules.message.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import xin.xiaoer.common.log.SysLog;
import xin.xiaoer.common.shiro.ShiroUtils;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;
import xin.xiaoer.entity.SysUser;
import xin.xiaoer.modules.message.entity.ActivityMessage;
import xin.xiaoer.modules.message.service.ActivityMessageService;

import java.util.List;
import java.util.Map;

/**
 * @author DaiMingJian
 * @email 3088393266@qq.com
 * @date 2018/12/21
 */

@Controller
@RequestMapping("activitymessage")
public class ActivityMessageController {

    @Autowired
    private ActivityMessageService activityMessageService;

    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("activitymessage:list")
    public String list() {
        return "activitymessage/list";
    }

    /**
     * 列表数据
     */
    @ResponseBody
    @RequestMapping("/listData")
    @RequiresPermissions("activitymessage:list")
    public R listData(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);
        SysUser admin = ShiroUtils.getUserEntity();
        if (admin.getUserId() != 1L){
            query.put("spaceId", admin.getSpaceId());
        }
        List<ActivityMessage> activityMessagesList = activityMessageService.getList(query);
        int total = activityMessageService.getCount(query);
        PageUtils pageUtil = new PageUtils(activityMessagesList, total, query.getLimit(), query.getPage());
        return R.ok().put("page", pageUtil);
    }

    /**
     * 删除
     */
    @ResponseBody
    @SysLog("删除短信息")
    @RequestMapping("/delete")
    @RequiresPermissions("activitymessage:delete")
    public R delete(@RequestBody Integer[] messageIds){
        activityMessageService.deleteBatch(messageIds);
        return R.ok();
    }


}
