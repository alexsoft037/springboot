package xin.xiaoer;

import org.junit.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * @Author jiangyujie【1512941128@qq.com】
 * @Title FileControllerTest
 * @Description TODO
 * @Package com.wlsj.common.utils
 * @Date 2018-06-05 14:23
 **/
public class FileControllerTest {
//    @Test
//    public void testFileDel() throws UnsupportedEncodingException {
//        RestTemplate rest = new RestTemplate();
//
//
//        String fileUrl = "http://10.63.22.23/group1/M00/00/08/Cj8WF1rYUeWAddsjAAACtTNvFwM452.png";
//
//        String encodeUrl = URLEncoder.encode(fileUrl,"utf-8");
//
//        String url = "http://10.63.22.25:8080/file/"+encodeUrl;
//
//        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
//        param.add("appkey", "jzfp_yjj_yanshi_app");
//        param.add("timestamp", new Date().getTime());
//        param.add("sign", "2212");
//        param.add("fileUrl", "http://10.63.22.23/group1/M00/00/08/Cj8WF1rYUeWAddsjAAACtTNvFwM452.png");
//        rest.delete(url, param);
//    }
//
//    //支持100M以内的文件上传
//    @org.junit.Test
//    public void testUploadFile(){
//        RestTemplate rest = new RestTemplate();
//        String url = "http://10.63.22.25:8080/file/upload";
//        String filePath = "C:\\Users\\Administrator\\Desktop\\testdata\\child.jpg";
//        FileSystemResource resource = new FileSystemResource(new File(filePath));
//        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
//        param.add("appkey", "jzfp_yjj_yanshi_app");
//        param.add("timestamp", new Date().getTime());
//        param.add("sign", "2212");
//        param.add("file", resource);
//        String string = rest.postForObject(url, param, String.class);
//        System.out.println(string);
//    }
//
//    //支持100M以内的文件上传
//    @org.junit.Test
//    public void testPicUploadFile(){
//        RestTemplate rest = new RestTemplate();
//        String url = "http://localhost:8080/file/picUpload";
//        String filePath = "C:\\Users\\Administrator\\Desktop\\testdata\\child.jpg";
//        FileSystemResource resource = new FileSystemResource(new File(filePath));
//        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
//        param.add("file", resource);
//        String string = rest.postForObject(url, param, String.class);
//        System.out.println(string);
//    }
//    /**
//     * 生成调用地址的方法
//     *
//     */
//    @org.junit.Test
//    public void testView(){
////        http://10.63.22.23/group1/M00/00/12/Cj8WF1sWKimAFoS8AARgkAaSRXE305.jpg
//        String url = "group1/M00/00/12/Cj8WF1sWKimAFoS8AARgkAaSRXE305.jpg";
//        int time = getSecondTimestamp(new Date());
//        String token = genToken(url,"FastDFS1234567890",time);
//        String viewUrl = url+"?token="+token+"&ts="+time;//将此URL放置到浏览器即可查看图片
//        System.out.println(viewUrl);
//    }
//
//    /**
//     *
//     * @param filepath 文件上传之后返回的路径
//     * @param key fastdfs设置的key，这里固定为：FastDFS1234567890
//     * @param time 取当前时间时分秒生成的int值
//     * @return
//     */
//    public  String genToken(String filepath, String key,int time){
//        String token = "";
//        String callPath = filepath.trim();
//        callPath = callPath.substring(callPath.indexOf("group")+7);
//        if(!StringUtils.isEmpty(callPath)){
//            try {
//                token = callPath+key+time;
//                token =   new String(token.getBytes(),"UTF-8");
//                token = hashKeyForDisk(token);
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//        }
//        return token;
//    }
//
//    /**
//     * 字符串hash算法
//     * @param key
//     * @return
//     */
//    public static String hashKeyForDisk(String key) {
//        String cacheKey;
//        try {
//            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
//            mDigest.update(key.getBytes());
//            cacheKey = bytesToHexString(mDigest.digest());
//        } catch (NoSuchAlgorithmException e) {
//            cacheKey = String.valueOf(key.hashCode());
//        }
//        return cacheKey;
//    }
//    private static String bytesToHexString(byte[] bytes) {
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < bytes.length; i++) {
//            String hex = Integer.toHexString(0xFF & bytes[i]);
//            if (hex.length() == 1) {
//                sb.append('0');
//            }
//            sb.append(hex);
//        }
//        return sb.toString();
//    }
//
//    /**
//     * 获取精确到秒的时间戳
//     * @return
//     */
//    public static int getSecondTimestamp(Date date){
//        if (null == date) {
//            return 0;
//        }
//        String timestamp = String.valueOf(date.getTime());
//        int length = timestamp.length();
//        if (length > 3) {
//            return Integer.valueOf(timestamp.substring(0,length-3));
//        } else {
//            return 0;
//        }
//    }
//    @org.junit.Test
//    public  void test1(){
//        String [] a = {"1","2","3"};
//        System.out.println(org.apache.commons.lang.StringUtils.join(a,","));
//    }
}
