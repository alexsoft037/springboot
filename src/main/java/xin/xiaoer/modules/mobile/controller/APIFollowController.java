package xin.xiaoer.modules.mobile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.web.bind.annotation.*;
import xin.xiaoer.common.oss.CloudStorageService;
import xin.xiaoer.common.oss.OSSFactory;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.modules.setting.entity.Follow;
import xin.xiaoer.modules.setting.service.FollowService;
import xin.xiaoer.service.SysUserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("mobile/follow")
@ApiIgnore
public class APIFollowController {

    @Autowired
    private FollowService followService;

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ResponseBean add(@RequestParam Map<String, Object> params) {
        String userId = params.get("userId").toString();
        String followId = params.get("followId").toString();

        Follow follow = followService.getByUserIdAndFollowId(Long.parseLong(userId), Long.parseLong(followId));

        boolean isFollowed;
        if (follow == null){
            follow = new Follow();
            follow.setUserId(Long.parseLong(userId));
            follow.setFollowId(Long.parseLong(followId));
            followService.save(follow);
            isFollowed = true;
        } else {
            followService.deleteByUserIdAndFollowId(Long.parseLong(userId), Long.parseLong(followId));
            isFollowed = false;
        }

        Map<String, Object> result = new HashMap<>();
        result.put("isFollowed", isFollowed);

        return new ResponseBean(false,"success", null, result);
    }

    @RequestMapping(value = "wodeguanzhu", method = RequestMethod.POST)
    public ResponseBean wodeguanzhu(@RequestParam Map<String, Object> params) {
        String userId = params.get("userId").toString();
        String pageCount = params.get("pageCount").toString();
        String curPageNum = params.get("curPageNum").toString();

        Map<String, Object> search = new HashMap<>();
        search.put("limit", pageCount);
        search.put("page", curPageNum);
        search.put("userId", userId);
        Query query = new Query(search);

        List<Follow> follows = followService.getListByUserId(search);
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (Follow follow: follows) {
            follow.setFollowId(follow.getUserId());
            follow.setUserId(Long.parseLong(userId));
            follow.getUser().setAvatar(cloudStorageService.generatePresignedUrl(follow.getUser().getAvatar()));
        }

        int total = followService.getCount(search);
        PageUtils pageUtil = new PageUtils(follows, total, query.getLimit(), query.getPage());

        return new ResponseBean(false,"success", null, pageUtil);
    }

    @RequestMapping(value = "wodefensi", method = RequestMethod.POST)
    public ResponseBean wodefensi(@RequestParam Map<String, Object> params) {
        String userId = params.get("userId").toString();
        String pageCount = params.get("pageCount").toString();
        String curPageNum = params.get("curPageNum").toString();

        Map<String, Object> search = new HashMap<>();
        search.put("limit", pageCount);
        search.put("page", curPageNum);
        search.put("followId", userId);
        Query query = new Query(search);

        List<Follow> follows = followService.getListByFollowId(search);
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (Follow follow: follows){
            follow.setFollowId(Long.parseLong(userId));
            follow.getUser().setAvatar(cloudStorageService.generatePresignedUrl(follow.getUser().getAvatar()));
        }

        int total = followService.getCount(search);
        PageUtils pageUtil = new PageUtils(follows, total, query.getLimit(), query.getPage());

        return new ResponseBean(false,"success", null, pageUtil);
    }

    @RequestMapping(value = "counts/{userId}", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseBean followCounts(@PathVariable("userId") Long userId) {

        Map<String, Object> param1 = new HashMap<>();
        param1.put("userId", userId);
        Map<String, Object> param2 = new HashMap<>();
        param2.put("followId", userId);

        int guanzhuTotal = followService.getCount(param1);
        int fensiTotal = followService.getCount(param2);

        Map<String, Object> result = new HashMap<>();
        result.put("guanzhuTotal", guanzhuTotal);
        result.put("fensiTotal", fensiTotal);

        return new ResponseBean(false,"success", null, result);
    }

}
