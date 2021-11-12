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
import xin.xiaoer.common.utils.StringUtility;
import xin.xiaoer.entity.Article;
import xin.xiaoer.entity.File;
import xin.xiaoer.entity.SysUser;
import xin.xiaoer.modules.favourite.entity.Favourite;
import xin.xiaoer.modules.favourite.service.FavouriteService;
import xin.xiaoer.modules.like.entity.Like;
import xin.xiaoer.modules.like.service.LikeService;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.modules.mobile.entity.HeadlineListItem;
import xin.xiaoer.modules.review.service.ReviewService;
import xin.xiaoer.modules.spacehaedline.entity.SpaceHeadline;
import xin.xiaoer.modules.spacehaedline.service.SpaceHeadlineService;
import xin.xiaoer.service.FileService;
import xin.xiaoer.service.SysUserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("mobile/spaceHeadline")
@ApiIgnore
public class APIHeadLineController {

    @Autowired
    private SpaceHeadlineService headlineService;

    @Autowired
    private FileService fileService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private FavouriteService favouriteService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private ReviewService reviewService;

    @RequestMapping(value = "/listData", method = RequestMethod.POST)
    public ResponseBean listData(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        //查询列表数据

//        String requestPageCount = request.getParameter("pageCount");
        String pageCount = params.get("pageCount").toString();
        String curPageNum = params.get("curPageNum").toString();
        String userId = params.get("userId").toString();
        String spaceId = params.get("spaceId").toString();
        if (StringUtility.isEmpty(spaceId)) {
            params.put("spaceId", 0);
        }
        params.put("limit", pageCount);
        params.put("page", curPageNum);
        params.put("state", "1");
        Query query = new Query(params);

        CloudStorageService cloudStorageService = OSSFactory.build();
        List<HeadlineListItem> headlineListItems = headlineService.queryListData(query);
        for (HeadlineListItem headlineListItem : headlineListItems) {
            SysUser sysUser = sysUserService.queryObject(headlineListItem.getCreateBy());
            String headLineId = Integer.toString(headlineListItem.getHeadlineId());
            Like like = likeService.getByArticleAndUser(Article.SPACE_HEADLINE, Long.parseLong(headLineId), Long.parseLong(userId));
            if (like == null){
                headlineListItem.setLikeYn(false);
            } else {
                headlineListItem.setLikeYn(true);
            }
            if (StringUtility.isEmpty(sysUser.getNickname())){
                headlineListItem.setCreateUser(headlineListItem.getAuthorName());
            } else {
                headlineListItem.setCreateUser(sysUser.getNickname());
            }

            if (!StringUtility.isEmpty(headlineListItem.getFeaturedImage())) {
                headlineListItem.setFeaturedImage(cloudStorageService.generatePresignedUrl(headlineListItem.getFeaturedImage()));
            }
        }
        int total = headlineService.countListData(query);

        PageUtils pageUtil = new PageUtils(headlineListItems, total, query.getLimit(), query.getPage());

        return new ResponseBean(false, "success", null, pageUtil);
    }

    @RequestMapping(value = "/detail/{headLineId}/{userId}", method = RequestMethod.GET)
    public ResponseBean detail(@PathVariable("headLineId") Integer headLineId, @PathVariable("userId") Long userId) {
        //查询列表数据

        SpaceHeadline spaceHeadline = headlineService.get(headLineId);
        if (!spaceHeadline.getState().equals("1")) {
            return new ResponseBean(false, ResponseBean.FAILED, "空", null);
        }
        spaceHeadline.setReadCount(spaceHeadline.getReadCount() + 1);
        headlineService.update(spaceHeadline);
        SysUser sysUser = sysUserService.queryObject(spaceHeadline.getCreateBy());
        if (sysUser != null) {
            if (StringUtility.isEmpty(sysUser.getNickname())) {
                spaceHeadline.setCreateUser(spaceHeadline.getAuthorName());
            } else {
                spaceHeadline.setCreateUser(sysUser.getNickname());
            }
        }
        Favourite favourite = favouriteService.getByArticleAndUser("AT0002", spaceHeadline.getHeadlineId().longValue(), userId);
        if (favourite != null) {
            spaceHeadline.setFavouriteYn(true);
        } else {
            spaceHeadline.setFavouriteYn(false);
        }
        Like like = likeService.getByArticleAndUser(Article.SPACE_HEADLINE, Long.parseLong(Integer.toString(headLineId)), userId);
        if (like == null){
            spaceHeadline.setLikeYn(false);
        } else {
            spaceHeadline.setLikeYn(true);
        }
        spaceHeadline.setReviewCount(reviewService.getCountByCodeAndId(Article.SPACE_HEADLINE, Long.parseLong(Integer.toString(headLineId))));
        List<File> files = fileService.getByRelationId(spaceHeadline.getFeaturedImage());
        CloudStorageService cloudStorageService = OSSFactory.build();
        if (files.size() > 0) {
            if (!StringUtility.isEmpty(files.get(0).getUrl())) {
                spaceHeadline.setFeaturedImage(cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
            } else {
                spaceHeadline.setFeaturedImage("");
            }
        } else {
            spaceHeadline.setFeaturedImage("");
        }

        return new ResponseBean(false, "success", null, spaceHeadline);
    }
}
