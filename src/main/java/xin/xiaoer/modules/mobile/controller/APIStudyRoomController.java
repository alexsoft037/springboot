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
import xin.xiaoer.modules.classroom.entity.StudyAttend;
import xin.xiaoer.modules.classroom.entity.StudyRoom;
import xin.xiaoer.modules.classroom.service.StudyAttendService;
import xin.xiaoer.modules.classroom.service.StudyRoomService;
import xin.xiaoer.modules.like.entity.Like;
import xin.xiaoer.modules.like.service.LikeService;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.modules.mobile.entity.StudyRoomListItem;
import xin.xiaoer.modules.review.service.ReviewService;
import xin.xiaoer.service.FileService;
import xin.xiaoer.service.SysUserService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("mobile/studyRoom")
@ApiIgnore
public class APIStudyRoomController {
    @Autowired
    private StudyRoomService studyRoomService;

    @Autowired
    private FileService fileService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private StudyAttendService studyAttendService;

    @RequestMapping(value = "/listData", method = RequestMethod.POST)
    public ResponseBean listData(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        //??????????????????

//        String requestPageCount = request.getParameter("pageCount");
        String pageCount = params.get("pageCount").toString();
        String curPageNum = params.get("curPageNum").toString();
        String studyRoomType = params.get("studyRoomType").toString();
        String spaceId = params.get("spaceId").toString();
        if (StringUtility.isEmpty(spaceId)) {
            params.put("spaceId", 0);
        }
        params.put("limit", pageCount);
        params.put("page", curPageNum);
        params.put("state", "1");
        params.put("srTypeCode", studyRoomType);
        Query query = new Query(params);

        CloudStorageService cloudStorageService = OSSFactory.build();

        List<StudyRoomListItem> studyRoomListItems = studyRoomService.getListData(query);
        for (StudyRoomListItem studyRoomListItem : studyRoomListItems) {
//            activityListItem.setAttendYN(false);
            Integer totalTotalAttends = studyAttendService.getCountByRoomId(studyRoomListItem.getRoomId());
            studyRoomListItem.setTotalAttends(totalTotalAttends);
            if (!StringUtility.isEmpty(studyRoomListItem.getFeaturedImage())) {
                studyRoomListItem.setFeaturedImage(cloudStorageService.generatePresignedUrl(studyRoomListItem.getFeaturedImage()));
            }
        }
        int total = studyRoomService.getCountData(query);

        PageUtils pageUtil = new PageUtils(studyRoomListItems, total, query.getLimit(), query.getPage());

        return new ResponseBean(false, "success", null, pageUtil);
    }

    @RequestMapping(value = "/detail/{roomId}/{userId}", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseBean deatil(@PathVariable("roomId") Integer roomId, @PathVariable("userId") Long userId) throws Exception {
        //??????????????????

        StudyRoom studyRoom = studyRoomService.get(roomId);
        if (!studyRoom.getState().equals("1")) {
            return new ResponseBean(false, ResponseBean.FAILED, "???", null);
        }
        studyRoom.setReadCount(studyRoom.getReadCount() + 1);
        studyRoomService.update(studyRoom);

        Like like = likeService.getByArticleAndUser(Article.STUDY_ROOM, Long.parseLong(Integer.toString(roomId)), userId);
        if (like == null){
            studyRoom.setLikeYn(false);
        } else {
            studyRoom.setLikeYn(true);
        }
        Integer reviewCount = reviewService.getCountByCodeAndId(Article.STUDY_ROOM, Long.parseLong(Integer.toString(roomId)));
        studyRoom.setReviewCount(reviewCount);

        SysUser sysUser = sysUserService.queryObject(studyRoom.getCreateBy());

        Integer totalTotalAttends = studyAttendService.getCountByRoomId(studyRoom.getRoomId());

        studyRoom.setCreateUser(sysUser.getNickname());
//        activity.setContactPhone(sysUser.getPhoneNo());
        studyRoom.setTotalAttends(totalTotalAttends);

        StudyAttend attend = studyAttendService.getByUserAndRoom(roomId, userId);
        if (attend != null){
            studyRoom.setAttendYN(true);
        } else {
            studyRoom.setAttendYN(false);
        }

        CloudStorageService cloudStorageService = OSSFactory.build();
        List<File> files = fileService.getByRelationId(studyRoom.getFeaturedImage());

        if (files.size() > 0) {
            if (!StringUtility.isEmpty(files.get(0).getUrl())) {
                studyRoom.setFeaturedImage(cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
            } else {
                studyRoom.setFeaturedImage("");
            }
        } else {
            studyRoom.setFeaturedImage("");
        }

        return new ResponseBean(false, "success", null, studyRoom);
    }

    @RequestMapping(value = "/attendList", method = RequestMethod.POST)
    public ResponseBean attendList(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        //??????????????????
        String pageCount = params.get("pageCount").toString();
        String curPageNum = params.get("curPageNum").toString();
        String roomId = params.get("roomId").toString();

        Map<String, Object> search = new HashMap<>();
        search.put("roomId", roomId);
        search.put("limit", pageCount);
        search.put("page", curPageNum);
        search.put("state", "1");

        Query query = new Query(search);
        List<StudyAttend> studyAttends = studyAttendService.getList(query);
        for (StudyAttend studyAttend: studyAttends){
            studyAttend.setAvatar(sysUserService.getAvatar(studyAttend.getAvatar()));
        }

        int total = studyAttendService.getCount(query);

        PageUtils pageUtil = new PageUtils(studyAttends, total, query.getLimit(), query.getPage());

        return new ResponseBean(false, "success", null, pageUtil);
    }

    @RequestMapping(value = "/applyAttend", method = RequestMethod.POST)
    public ResponseBean saveAttend(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        //??????????????????
        String roomId = params.get("roomId").toString();
        String userId = params.get("userId").toString();

        StudyAttend attend = studyAttendService.getByUserAndRoom(Integer.parseInt(roomId), Long.parseLong(userId));
        if (attend != null) {
            return new ResponseBean(false, "success", null, "already_applied","already_applied");
        } else {
            StudyAttend studyAttend = new StudyAttend();
            studyAttend.setRoomId(Integer.parseInt(roomId));
            studyAttend.setUserId(Long.parseLong(userId));
            studyAttendService.save(studyAttend);
        }


        return new ResponseBean(false, "success", null, null);
    }
}
