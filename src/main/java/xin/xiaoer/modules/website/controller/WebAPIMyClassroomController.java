package xin.xiaoer.modules.website.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import xin.xiaoer.common.oss.CloudStorageService;
import xin.xiaoer.common.oss.OSSFactory;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.StringUtility;
import xin.xiaoer.modules.classroom.service.SharingLessonService;
import xin.xiaoer.modules.classroom.service.StudyAttendService;
import xin.xiaoer.modules.classroom.service.StudyRoomService;
import xin.xiaoer.modules.comment.service.CommentService;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.modules.mobile.entity.StudyRoomListItem;
import xin.xiaoer.modules.mobile.entity.VideoLessonListItem;
import xin.xiaoer.service.FileService;
import xin.xiaoer.service.SysUserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("website/myClassroom")
@ApiIgnore
public class WebAPIMyClassroomController {
    @Autowired
    private StudyRoomService studyRoomService;

    @Autowired
    private FileService fileService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private StudyAttendService studyAttendService;

    @Autowired
    private SharingLessonService sharingLessonService;

    //查用户预约的学习室信息列表
    @RequestMapping(value = "/attendList", method = RequestMethod.POST)
    public ResponseBean attendList(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        //查询列表数据

//        String requestPageCount = request.getParameter("pageCount");
        String pageCount = params.get("pageCount").toString();
        String curPageNum = params.get("curPageNum").toString();
        String userId = params.get("userId").toString();
        String sort = params.get("sort").toString();
        if (StringUtility.isEmpty(sort)) {
            params.put("sort", "asc");
        }
        params.put("limit", pageCount);
        params.put("page", curPageNum);
        params.put("state", "1");
//        params.put("userId", userId);
        Query query = new Query(params);

        CloudStorageService cloudStorageService = OSSFactory.build();

        List<StudyRoomListItem> studyRoomListItems = studyRoomService.getListDataByUser(query);
        for (StudyRoomListItem studyRoomListItem : studyRoomListItems) {
            Integer totalTotalAttends = studyAttendService.getCountByRoomId(studyRoomListItem.getRoomId());
            studyRoomListItem.setTotalAttends(totalTotalAttends);
            if (!StringUtility.isEmpty(studyRoomListItem.getFeaturedImage())) {
                studyRoomListItem.setFeaturedImage(cloudStorageService.generatePresignedUrl(studyRoomListItem.getFeaturedImage()));
            }
        }
        int total = studyRoomService.getCountDataByUser(query);

        PageUtils pageUtil = new PageUtils(studyRoomListItems, total, query.getLimit(), query.getPage());

        return new ResponseBean(false, "success", null, pageUtil);
    }

    //获取用户看过的课程列表
    @RequestMapping(value = "/videoList", method = RequestMethod.POST)
    public ResponseBean videoList(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        //查询列表数据
        String pageCount = params.get("pageCount").toString();
        String curPageNum = params.get("curPageNum").toString();
        String userId = params.get("userId").toString();
        String sort = params.get("sort").toString();
        if (StringUtility.isEmpty(sort)) {
            params.put("sort", "asc");
        }

        params.put("limit", pageCount);
        params.put("page", curPageNum);
        params.put("state", "1");
        Query query = new Query(params);

        CloudStorageService cloudStorageService = OSSFactory.build();
        List<VideoLessonListItem> sharingLessons = sharingLessonService.getApiListDataByUser(query);
        for (VideoLessonListItem sharingLesson : sharingLessons) {
            sharingLesson.setAvatar(cloudStorageService.generatePresignedUrl(sharingLesson.getAvatar()));
            sharingLesson.setFeaturedImage(cloudStorageService.generatePresignedUrlWithResize(sharingLesson.getFeaturedImage(), "400", "200"));
        }

        int total = sharingLessonService.getApiCountByUser(query);

        PageUtils pageUtil = new PageUtils(sharingLessons, total, query.getLimit(), query.getPage());

        return new ResponseBean(false, "success", null, pageUtil);
    }

    //获取用户上传课程列表
    @RequestMapping(value = "/sharedList", method = RequestMethod.POST)
    public ResponseBean sharedList(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        //查询列表数据
        String pageCount = params.get("pageCount").toString();
        String curPageNum = params.get("curPageNum").toString();
        String userId = params.get("userId").toString();
        String sort = params.get("sort").toString();
        if (StringUtility.isEmpty(sort)) {
            params.put("sort", "asc");
        }
        params.put("limit", pageCount);
        params.put("page", curPageNum);
        params.put("createBy", userId);
        params.put("state", "1");
        Query query = new Query(params);

        CloudStorageService cloudStorageService = OSSFactory.build();
        List<VideoLessonListItem> sharingLessons = sharingLessonService.getApiListData(query);
        for (VideoLessonListItem sharingLesson : sharingLessons) {
            sharingLesson.setAvatar(cloudStorageService.generatePresignedUrl(sharingLesson.getAvatar()));
            sharingLesson.setFeaturedImage(cloudStorageService.generatePresignedUrlWithResize(sharingLesson.getFeaturedImage(), "400", "200"));
        }

        int total = sharingLessonService.getApiCount(query);

        PageUtils pageUtil = new PageUtils(sharingLessons, total, query.getLimit(), query.getPage());

        return new ResponseBean(false, "success", null, pageUtil);
    }
}
