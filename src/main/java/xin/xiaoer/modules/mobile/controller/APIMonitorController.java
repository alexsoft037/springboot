package xin.xiaoer.modules.mobile.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import xin.xiaoer.common.oss.CloudStorageService;
import xin.xiaoer.common.oss.OSSFactory;
import xin.xiaoer.common.utils.EzvizUtils;
import xin.xiaoer.common.utils.StringUtility;
import xin.xiaoer.entity.File;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.modules.mobile.entity.LostNotice;
import xin.xiaoer.modules.mobile.entity.TimeListItem;
import xin.xiaoer.modules.monitor.entity.*;
import xin.xiaoer.modules.monitor.service.*;
import xin.xiaoer.service.FileService;

import javax.servlet.http.HttpServletRequest;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

@RestController
@RequestMapping("mobile/trajectory")
@ApiIgnore
public class APIMonitorController {

    @Value("${ezviz.appKey}")
    private String appKey;

    @Value("${ezviz.appSecret}")
    private String appSecret;

    private Process process;

    @Autowired
    private ChildrenLostService childrenLostService;

    @Autowired
    private SpaceCameraService spaceCameraService;

    @Autowired
    private ChildrenWatichService childrenWatichService;

    @Autowired
    private ChildrenTrajectoryService childrenTrajectoryService;

    @Autowired
    private UserChildrenService userChildrenService;

    @Autowired
    private CameraCaptureService cameraCaptureService;

    @Autowired
    private FileService fileService;

    @Autowired
    private CameraRecordService cameraRecordService;

    @RequestMapping(value = "/notices", method = RequestMethod.POST)
    public ResponseBean banner(@RequestParam Map<String, Object> params){
        String spaceId = params.get("spaceId").toString();
        if (StringUtility.isEmpty(spaceId)) {
            params.put("spaceId", 0);
        }
       List<LostNotice> lostNoticeList = childrenLostService.getNoticeList(params);

        return new ResponseBean(false,"success", null, lostNoticeList);
    }

    @RequestMapping(value = "/noticeDetail/{noticeId}", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseBean noticeDetail(@PathVariable("noticeId") Integer noticeId) {

        ChildrenLost childrenLost = childrenLostService.get(noticeId);
        List<File> files = fileService.getByRelationId(childrenLost.getPhoto());
        CloudStorageService cloudStorageService = OSSFactory.build();
        if (files.size() > 0){
            childrenLost.setPhoto(cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
        } else {
            childrenLost.setPhoto("");
        }

        return new ResponseBean(false,"success", null, childrenLost);
    }

    @RequestMapping(value = "/lostPath/{noticeId}", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseBean lostPath(@PathVariable("noticeId") Integer noticeId) {

        ChildrenLost childrenLost = childrenLostService.get(noticeId);
        UserChildren userChildren = userChildrenService.get(childrenLost.getChildrenId());
        CloudStorageService cloudStorageService = OSSFactory.build();
        List<File> files = fileService.getByRelationId(userChildren.getPhoto());
        if (files.size() > 0){
            userChildren.setPhoto(cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
        } else {
            userChildren.setPhoto("");
        }

        Map<String, Object> params = new HashMap<>();
        params.put("day", childrenLost.getLostTime());
        List<ChildrenTrajectory> trajectoryList = childrenTrajectoryService.getPointList(params);

        Map<String, Object> result = new HashMap<>();
        result.put("avatar", userChildren.getPhoto());
        result.put("points", trajectoryList);

        return new ResponseBean(false,"success", null, result);
    }

    @RequestMapping(value = "/allVideos", method = RequestMethod.POST)
    public ResponseBean allVideos(@RequestParam Map<String, Object> params ){

        String spaceId = params.get("spaceId").toString();
        if (StringUtility.isEmpty(spaceId)) {
            params.put("spaceId", 0);
        }
        params.put("state", "1");
        List<SpaceCamera> spaceCameraList = spaceCameraService.getList(params);
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (SpaceCamera spaceCamera: spaceCameraList){
            List<File> files = fileService.getByRelationId(spaceCamera.getImg());
            if (files.size() < 1){
                spaceCamera.setImg("");
            } else {
                spaceCamera.setImg(cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
            }
        }
        return new ResponseBean(false,"success", null, spaceCameraList);
    }

    @RequestMapping(value = "/webhooks", method = RequestMethod.POST)
    public ResponseBean webhooks(@RequestParam Map<String, Object> params, HttpServletRequest request){

        String deviceId = request.getParameter("deviceId");
        String lat = request.getParameter("lat");
        String lng = request.getParameter("lng");

        ChildrenWatich childrenWatich = childrenWatichService.getByDeviceId(deviceId);

        if (childrenWatich == null){
            return new ResponseBean(false,ResponseBean.FAILED, "unknown device", null);
        }

        ChildrenTrajectory childrenTrajectory = new ChildrenTrajectory();
        childrenTrajectory.setChildrenId(childrenWatich.getChildrenId());
        childrenTrajectory.setWatchId(childrenWatich.getWatchId());
        childrenTrajectory.setLatitude(lat);
        childrenTrajectory.setLongitude(lng);

        childrenTrajectoryService.save(childrenTrajectory);

        return new ResponseBean(false,"success", null, null);
    }

    @RequestMapping(value = "/pointList", method = RequestMethod.POST)
    public ResponseBean pointList(HttpServletRequest request) {

        UserChildren userChildren = userChildrenService.get(1L);
        CloudStorageService cloudStorageService = OSSFactory.build();
        List<File> files = fileService.getByRelationId(userChildren.getPhoto());
        if (files.size() > 0){
            userChildren.setPhoto(cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
        } else {
            userChildren.setPhoto("");
        }

        String day = request.getParameter("day");
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        if(StringUtility.isNotEmpty(from)){
            from = day + " " + from;
        }
        if(StringUtility.isNotEmpty(to)){
            to =  day + " " + to;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("day", day);
        params.put("from", from);
        params.put("to", to);

        List<ChildrenTrajectory> childrenTrajectories = childrenTrajectoryService.getPointList(params);

        Map<String, Object> result = new HashMap<>();
        result.put("avatar", userChildren.getPhoto());
        result.put("points", childrenTrajectories);
        return new ResponseBean(false,"success", null, result);
    }

    @RequestMapping(value = "/timeDataList", method = RequestMethod.POST)
    public ResponseBean timeList(@RequestParam Map<String, Object> params, HttpServletRequest request){

        String day = request.getParameter("day");
        List<String> timeList = childrenTrajectoryService.getTimeList(day);
        ArrayList<TimeListItem> timeListItems = new ArrayList<>();
        for (String time: timeList) {
            TimeListItem timeListItem = new TimeListItem();
            timeListItem.setTime(time);
            time = day + " " + time;
            List<ChildrenTrajectory> childrenTrajectories = childrenTrajectoryService.getTimeDataList(time);

            double distance = 0;
            for (int i = 0; i < childrenTrajectories.size() - 1; i++) {
                ChildrenTrajectory current = childrenTrajectories.get(i);
                ChildrenTrajectory next = childrenTrajectories.get(i+1);
                distance += getDistanceInKm(Double.parseDouble(current.getLatitude()),
                        Double.parseDouble(current.getLongitude()),
                        Double.parseDouble(next.getLatitude()),
                        Double.parseDouble(next.getLongitude()));
            }
            timeListItem.setDistance(distance);
            timeListItems.add(timeListItem);
        }

        return new ResponseBean(false,"success", null, timeListItems);
    }

    @RequestMapping(value = "/cameraCapture/{deviceId}", method = RequestMethod.POST)
    public ResponseBean cameraCapture(@PathVariable("deviceId") Integer deviceId, HttpServletRequest request) {

        String userId = request.getParameter("userId");
        String spaceId = request.getParameter("spaceId");

        if (!StringUtility.isNumeric(userId) || !StringUtility.isNumeric(spaceId)) {
            return new ResponseBean(false,ResponseBean.FAILED, "userId and spaceId can't not be empty", "");
        }
        SpaceCamera spaceCamera = spaceCameraService.get(deviceId);

        String imageUrl = "";
        String error = "";
        String imageId = UUID.randomUUID().toString();
        CloudStorageService cloudStorageService = OSSFactory.build();
        try {
            EzvizUtils ezvizUtils = new EzvizUtils(appKey, appSecret);
            String picUrl = ezvizUtils.getCapture(spaceCamera.getSerialNo());

            URL url = new URL(picUrl);
            URLConnection conn = url.openConnection();

            imageUrl = cloudStorageService.uploadSuffix(conn.getInputStream(), ".jpg");
            File imageFile = new File();
            imageFile.setUploadId(imageId);
            imageFile.setUrl(imageUrl);
            imageFile.setFileName("capture.jpg");
            imageFile.setOssYn("Y");
            imageFile.setFileType("image");
            imageFile.setFileSize(Integer.toString(conn.getContentLength()));
            if (StringUtils.isNumeric(spaceId)){
                imageFile.setSpaceId(Integer.parseInt(spaceId));
            }
            fileService.save(imageFile);

            CameraCapture cameraCapture = new CameraCapture();
            cameraCapture.setDeviceId(deviceId);
            cameraCapture.setUserId(Long.parseLong(userId));
            cameraCapture.setPicUrl(imageId);
            if (StringUtils.isNumeric(spaceId)) {
                cameraCapture.setSpaceId(Integer.parseInt(spaceId));
            }

            cameraCaptureService.save(cameraCapture);
        }catch (Exception e){
            e.printStackTrace();
            error = e.getMessage();
            return new ResponseBean(false,ResponseBean.FAILED, error, error);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("picUrl", cloudStorageService.generatePresignedUrl(imageUrl));

        return new ResponseBean(false,"success", null, result);
    }

//    @RequestMapping(value = "/cameraRecord/{deviceId}/{userId}")
//    public ResponseBean cameraRecord(@PathVariable("deviceId") Integer deviceId, @PathVariable("userId") Long userId) throws InterruptedException, IOException {
//
//        SpaceCamera spaceCamera = spaceCameraService.get(deviceId);
//
//        String ffile = DateUtil.getUploadDir();
//
//        String fileName = UUID.randomUUID().toString().trim() + ".mp4";
//        String filePath = PathUtil.getClassResources() + Const.FILEPATH_STATIC + Const.FILEPATH_UPLOAD + ffile + fileName;
//        java.io.File file = new java.io.File(filePath);
//        if (!file.exists()) {
//            if (!file.getParentFile().exists()) {
//                file.getParentFile().mkdirs();
//            }
//        }
//
//        String ffmpegCommand = "ffmpeg -i " + spaceCamera.getUrl() + " -acodec copy -vcodec copy " + filePath;
//        Runtime currentRuntime = Runtime.getRuntime();
////                    currentRuntime.
//        Process proc = Runtime.getRuntime().exec(ffmpegCommand);
//        APIMonitorController.this.process = proc;
////        proc.waitFor();
//
//
//        CameraRecord cameraRecord = new CameraRecord();
//        cameraRecord.setUserId(userId);
//        cameraRecord.setDeviceId(deviceId);
//        cameraRecord.setRecordUrl(ffile + fileName);
////        cameraRecord.setThreadId(Long.toString(command.getId()));
//        cameraRecordService.save(cameraRecord);
//
//        Map<String, Object> result = new HashMap<>();
//        result.put("recordId", cameraRecord.getId());
//
//        return new ResponseBean(false,"success", null, result);
//    }
//
//    @RequestMapping(value = "/stopRecord/{recordId}")
//    public ResponseBean stopRecord(@PathVariable("recordId") Long recordId, HttpServletRequest request) throws MalformedURLException, IOException {
//
//        CameraRecord cameraRecord = cameraRecordService.get(recordId);
//        Long threadId = Long.parseLong(cameraRecord.getThreadId());
//
////        //Give you set of Threads
////        Set<Thread> setOfThread = Thread.getAllStackTraces().keySet();
////
////        //Iterate over set to find yours
////        for(Thread thread : setOfThread) {
////            if(thread.getId()== threadId){
////                thread.interrupt();
////            }
////        }
//
//        OutputStream ostream = process.getOutputStream(); //Get the output stream of the process, which translates to what would be user input for the commandline
//        ostream.write("q/n".getBytes());       //write out the character Q, followed by a newline or carriage return so it registers that Q has been 'typed' and 'entered'.
//        ostream.flush();
//
//        CloudStorageService cloudStorageService = OSSFactory.build();
//        com.alibaba.fastjson.JSONObject jsonObject = new JSONObject();
//        String videoId = UUID.randomUUID().toString();
//
//        String filePath = PathUtil.getClassResources() + Const.FILEPATH_STATIC + Const.FILEPATH_UPLOAD + cameraRecord.getRecordUrl();
//        java.io.File localFile = new java.io.File(filePath);
//        FileInputStream fs = new FileInputStream(localFile);
//
//        String videoUrl = cloudStorageService.uploadSuffix(fs, ".mp4");
//        File videoFile = new File();
//        videoFile.setUploadId(videoId);
//        videoFile.setUrl(videoUrl);
//        videoFile.setFileName("record.mp4");
//        videoFile.setOssYn("Y");
//        videoFile.setFileType("video");
//        videoFile.setFileSize(Long.toString(localFile.length()));
//
//        fileService.save(videoFile);
//
//        cameraRecord.setRecordUrl(videoId);
//        cameraRecordService.update(cameraRecord);
//
//        return new ResponseBean(false,"success", null, cameraRecord);
//    }
//

    @RequestMapping(value = "/captureList", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseBean captureList(@RequestParam Map<String, Object> params, HttpServletRequest request){

        String userId = request.getParameter("userId");
        Map<String, Object> query = new HashMap<>();
        query.put("userId", userId);
        List<String> dateList = cameraCaptureService.getDateList(query);

        ArrayList<Map<String, Object>> timeListItems = new ArrayList<>();
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (String time: dateList){
            Map<String, Object> listItem = new HashMap<>();
            Map<String, Object> search = new HashMap<>();
            search.put("date", time);
            search.put("userId", userId);
            List<CameraCapture> list = cameraCaptureService.getCaptureList(search);
            for (CameraCapture cameraCapture: list) {
                cameraCapture.setPicUrl(cloudStorageService.generatePresignedUrl(cameraCapture.getPicUrl()));
            }
            listItem.put("date", time);
            listItem.put("list", list);
            timeListItems.add(listItem);
        }

        return new ResponseBean(false,"success", null, timeListItems);
    }

    private double degreesToRadians(double degrees) {
        return degrees * Math.PI / 180;
    }

    private double getDistanceInKm(double lat1, double lon1, double lat2, double lon2) {
        double R = 6378.137; // Radius of earth in KM
        double dLat = lat2 * Math.PI / 180 - lat1 * Math.PI / 180;
        double dLon = lon2 * Math.PI / 180 - lon1 * Math.PI / 180;
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c;
        return d; // kilometers
    }
}
