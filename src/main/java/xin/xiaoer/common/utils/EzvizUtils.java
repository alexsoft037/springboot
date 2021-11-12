package xin.xiaoer.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;

public class EzvizUtils {

    private String appKey;

    private String appSecret;

    private static RestTemplate restTemplate = new RestTemplate();

    private String accessToken;

    public EzvizUtils(String appKey, String appSecret){
        setAppKey(appKey);
        setAppSecret(appSecret);
        getAccessToken();
    }

    private void getAccessToken () {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("appKey", appKey);
        paramMap.add("appSecret", appSecret);

        String tokenUrl = "https://open.ys7.com/api/lapp/token/get";

        String faren = restTemplate.postForEntity(tokenUrl, paramMap, String.class).getBody();
        com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject.parseObject(faren);
        String code = json.getString("code");
        if (code.equals("200")){
            this.accessToken = json.getJSONObject("data").getString("accessToken");
        } else {
            throw new RRException(json.get("msg").toString());
        }
    }

    public String getCapture(String serialNo) {
        String picUrl = "";
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("accessToken", accessToken);
        paramMap.add("deviceSerial", serialNo);
        paramMap.add("channelNo", "1");

        String tokenUrl = "https://open.ys7.com/api/lapp/device/capture";

        String faren = restTemplate.postForEntity(tokenUrl, paramMap, String.class).getBody();
        com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject.parseObject(faren);
        String code = json.getString("code");
        if (code.equals("200")){
            picUrl = json.getJSONObject("data").getString("picUrl");
        } else {
            throw new RRException(json.get("msg").toString());
        }

        return picUrl;
    }

    public com.alibaba.fastjson.JSONObject searchRecordFileFromDevice(String serialNo, String cameraNo, String startTime, String endTime){
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
//        final String fStartTime = Utils.calendar2String(startTime);
//        final String fEndTime = Utils.calendar2String(endTime);
        paramMap.add("accessToken", accessToken);
        paramMap.add("deviceSerial", serialNo);
        paramMap.add("cameraNo", cameraNo);
        paramMap.add("channelNo", cameraNo);
        paramMap.add("startTime", startTime);
        paramMap.add("endTime", endTime);
        paramMap.add("clientType", "13");
        paramMap.add("featureCode", "4cdcc821d9b3223230a900341ca1963b");
        paramMap.add("osVersion", "5.1.1");
        paramMap.add("sdkVersion", "v4.8.2.201803015");
        paramMap.add("netType", "WIFI");
        paramMap.add("appKey", appKey);
        paramMap.add("appName", "少儿空间");
        paramMap.add("appID", "com.xiaoer");
        paramMap.add("fileType", "0");
        paramMap.add("version", "2.0");

//        String tokenUrl = "https://open.ys7.com/api/sdk/device/local/video";
        String tokenUrl = "https://open.ys7.com/api/cloud/files/get";

        String faren = restTemplate.postForEntity(tokenUrl, paramMap, String.class).getBody();
        JSONObject json = JSONObject.parseObject(faren);
//        String code = json.getString("code");
//        if (code.equals("200")){
//
//        } else {
//            throw new RRException(json.get("code").toString());
//        }

        return json;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppKey() {
        return appKey;
    }
}
