package xin.xiaoer.modules.website.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xin.xiaoer.common.oss.CloudStorageService;
import xin.xiaoer.common.oss.OSSFactory;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.entity.Article;
import xin.xiaoer.entity.File;
import xin.xiaoer.modules.favourite.service.FavouriteService;
import xin.xiaoer.modules.like.service.LikeService;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.modules.review.service.ReviewService;
import xin.xiaoer.modules.website.entity.DonateWeb;
import xin.xiaoer.modules.website.service.DonateWebService;
import xin.xiaoer.service.FileService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("website/donateWeb")
@Api("网站公益行动")
public class WebAPIDonateWebController {
    @Autowired
    private DonateWebService donateWebService;
    @Autowired
    private FileService fileService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private FavouriteService favouriteService;

    /**
     * @return xin.xiaoer.modules.mobile.bean.ResponseBean
     * @Author dong
     * @Description //TODO 公益详情
     * @Param [donateId]
     **/
    @ApiOperation("公益详情")
    @PostMapping("/detail/{donateId}")
    @ResponseBody
    public ResponseBean detail(@PathVariable("donateId") Integer donateId, Long userId) {
        DonateWeb donateWeb = donateWebService.getById(donateId);
        if (donateWeb == null || !"1".equals(donateWeb.getState().toString())) {
//            return R.error("无效的id");
            return new ResponseBean(false, ResponseBean.FAILED, "参数异常", "无效的id", null);
        }
        Integer readCount = donateWeb.getReadCount();
        if (readCount != null) {
            readCount++;
            donateWeb.setReadCount(readCount);
        }
        donateWebService.update(donateWeb);
        CloudStorageService cloudStorageService = OSSFactory.build();
        List<File> files = fileService.getByRelationId(donateWeb.getFeaturedImage());
        if (files.size() > 0) {
            donateWeb.setFeaturedImage(cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
        }

        if (userId != null) {
            Map params = new HashMap();
            params.put("articleId", donateId);
            params.put("articleTypeCode", Article.DONATE_WEB);
            params.put("userId", userId);
            List list = likeService.getList(params);
            if (list.size() > 0) {
                donateWeb.setLikeYN(true);
            } else {
                donateWeb.setLikeYN(false);
            }
            List list1 = favouriteService.getList(params);
            if (list1.size() > 0) {
                donateWeb.setFavouriteYN(true);
            } else {
                donateWeb.setFavouriteYN(false);
            }

        } else {
            donateWeb.setLikeYN(false);
            donateWeb.setFavouriteYN(false);
        }
        //点赞数
        donateWeb.setLikeCount(likeService.getCountByCodeAndId(Article.DONATE_WEB, Long.parseLong(donateId.toString())));
        //评论数
        donateWeb.setReviewCount(reviewService.getCountByCodeAndId(Article.DONATE_WEB, Long.parseLong(donateId.toString())));
        //收藏数
        donateWeb.setFavouriteCount(favouriteService.getCountByCodeAndId(Article.DONATE_WEB, Long.parseLong(donateId.toString())));


        return new ResponseBean(false, ResponseBean.SUCCESS, null, donateWeb);
    }


    /**
     * @return xin.xiaoer.modules.mobile.bean.ResponseBean
     * @Author dong
     * @Description //TODO 最新公益,四条
     * @Param []
     **/
    @ApiOperation("最新公益")
    @GetMapping("/newDonate")
    @ResponseBody
    public ResponseBean newDonate() {
        Map params = new HashMap();
        params.put("page", "1");
        params.put("limit", "4");
        Query query = new Query(params);
        List<DonateWeb> list = donateWebService.getNewDonate(query);
        CloudStorageService oss = OSSFactory.build();
        for (DonateWeb donateWeb : list) {
            List<File> files = fileService.getByRelationId(donateWeb.getFeaturedImage());
            if (files.size() > 0) {
                donateWeb.setFeaturedImage(oss.generatePresignedUrl(files.get(0).getUrl()));
            }
        }
//        return R.ok().put("list",list);
        return new ResponseBean(false, ResponseBean.SUCCESS, null, list);
    }


    /**
     * @return xin.xiaoer.modules.mobile.bean.ResponseBean
     * @Author dong
     * @Description //TODO 热门公益,四条
     * @Param []
     **/
    @ApiOperation("热门公益")
    @GetMapping("/hotDonate")
    @ResponseBody
    public ResponseBean getHotDonate() {
        Map params = new HashMap();
        params.put("page", "1");
        params.put("limit", "4");
        Query query = new Query(params);
        List<DonateWeb> list = donateWebService.getHotDonate(query);
        CloudStorageService oss = OSSFactory.build();
        for (DonateWeb donateWeb : list) {
            List<File> files = fileService.getByRelationId(donateWeb.getFeaturedImage());
            if (files.size() > 0) {
                donateWeb.setFeaturedImage(oss.generatePresignedUrl(files.get(0).getUrl()));
            }
        }
//        return R.ok().put("list",list);
        return new ResponseBean(false, ResponseBean.SUCCESS, null, list);
    }


    /**
     * @return xin.xiaoer.modules.mobile.bean.ResponseBean
     * @Author dong
     * @Description //TODO 公益行动标题列表
     * @Param [params]
     **/
    @ApiOperation("公益行动标题列表")
    @RequestMapping(value = "/getTitles", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean getTitles(@RequestParam Map<String, Object> params) {
        if (params.get("page") == null || params.get("limit") == null || StringUtils.isBlank(params.get("page").toString()) || StringUtils.isBlank(params.get("limit").toString())) {
//            return R.error("错误参数");
            return new ResponseBean(false, ResponseBean.FAILED, "参数异常", "缺少参数", null);
        }
        params.put("state", 1);
        params.put("limit",20);
        Query query = new Query(params);
        List<DonateWeb> list = donateWebService.getTitles(query);
        int total = donateWebService.getCount(query);
        PageUtils pageUtils = new PageUtils(list, total, query.getLimit(), query.getPage());
//        return R.ok().put("list", pageUtils);
        return new ResponseBean(false, ResponseBean.SUCCESS, null, pageUtils);
    }
}
