package xin.xiaoer.samples;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.GetObjectRequest;
import xin.xiaoer.common.utils.Const;
import xin.xiaoer.common.utils.DateUtil;
import xin.xiaoer.common.utils.DateUtility;
import xin.xiaoer.common.utils.PathUtil;

/**
 * 断点续传下载用法示例
 *
 */
public class ImageSample {
    
    private static String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    private static String accessKeyId = "LTAIGDEEo84OsWLa";
    private static String accessKeySecret = "aJtOKErH58ezXlcF2b3H78qiC2HVZj";
    private static String bucketName = "xiaertest";
    private static String key = "upload/20180816/98b4648e05a04b4ca6b5cc0ade2205f9.jpg";
    
    
    public static void main(String[] args) throws IOException {        

        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        
        try {
            // 缩放
            String style = "image/resize,m_fixed,w_100,h_100";  
            GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, key);
            request.setProcess(style);
//            request.setUseUrlSignature(true);
            URL path = ossClient.generatePresignedUrl(bucketName, key, DateUtil.changeStrToDate("2019-01-01"));

//            ossClient.getObject(request, new File(PathUtil.getClassResources() + Const.FILEPATH_STATIC + Const.FILEPATH_UPLOAD +"example-resize.jpg"));

            // 裁剪
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            Date tomorrow  = calendar.getTime();
            style = "image/crop,w_100,h_100";
            request = new GeneratePresignedUrlRequest(bucketName, key);
            request.setProcess(style);
            request.setExpiration(tomorrow);


            URL cropKey = ossClient.generatePresignedUrl(request);
            System.out.println(cropKey);
//            // 旋转
//            style = "image/rotate,90";
//            request = new GetObjectRequest(bucketName, key);
//            request.setProcess(style);
//
//            ossClient.getObject(request, new File(PathUtil.getClassResources() + Const.FILEPATH_STATIC + Const.FILEPATH_UPLOAD +"example-rotate.jpg"));
//
//            // 锐化
//            style = "image/sharpen,100";
//            request = new GetObjectRequest(bucketName, key);
//            request.setProcess(style);
//
//            ossClient.getObject(request, new File(PathUtil.getClassResources() + Const.FILEPATH_STATIC + Const.FILEPATH_UPLOAD +"example-sharpen.jpg"));
//
//            // 水印
//            style = "image/watermark,text_SGVsbG8g5Zu-54mH5pyN5YqhIQ";
//            request = new GetObjectRequest(bucketName, key);
//            request.setProcess(style);
//
//            ossClient.getObject(request, new File(PathUtil.getClassResources() + Const.FILEPATH_STATIC + Const.FILEPATH_UPLOAD +"example-watermark.jpg"));
//
//            // 格式转换
//            style = "image/format,png";
//            request = new GetObjectRequest(bucketName, key);
//            request.setProcess(style);
//
//            ossClient.getObject(request, new File(PathUtil.getClassResources() + Const.FILEPATH_STATIC + Const.FILEPATH_UPLOAD +"example-format.png"));
//
//            // 图片信息
//            style = "image/info";
//            request = new GetObjectRequest(bucketName, key);
//            request.setProcess(style);
//
//            ossClient.getObject(request, new File(PathUtil.getClassResources() + Const.FILEPATH_STATIC + Const.FILEPATH_UPLOAD + "example-info.txt"));
            
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message: " + oe.getErrorCode());
            System.out.println("Error Code:       " + oe.getErrorCode());
            System.out.println("Request ID:      " + oe.getRequestId());
            System.out.println("Host ID:           " + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ce.getMessage());
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }
    }
}
