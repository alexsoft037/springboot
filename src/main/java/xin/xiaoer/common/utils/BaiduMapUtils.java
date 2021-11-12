package xin.xiaoer.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class BaiduMapUtils {

    private String appKey = Constant.BaiduAK;

    private static RestTemplate restTemplate = new RestTemplate();

    public BaiduMapUtils(){
    }


    public BaiduMapUtils(String appKey){
        setAppKey(appKey);
    }

    private void getAccessToken () {
    }

    public JSONObject getAddressFromLatLong(String lat, String lng) throws Exception {
        String address = "";
        Map<String, String> paramsMap = new LinkedHashMap<>();
        paramsMap.put("location", lat + "," + lng);
//        paramsMap.put("address", "百度大厦");
        paramsMap.put("output", "json");
        paramsMap.put("ak", appKey);

        // 调用下面的MD5方法得到最后的sn签名7de5a22212ffaa9e326444c75a58f9a0
        String sn = SnCal.getSn("/geocoder/v2/", paramsMap);
        String paramsStr = SnCal.toQueryString(paramsMap);
        String apiUrl = "http://api.map.baidu.com/geocoder/v2/?" + paramsStr + "&sn=" + sn;
        HttpClient client = new DefaultHttpClient();
        HttpGet httpget = new HttpGet( apiUrl);
        HttpResponse response = client.execute(httpget);
        InputStream is = response.getEntity().getContent();
        String result = SnCal.inStream2String(is);
        JSONObject json = JSONObject.parseObject(result);
        String code = json.getString("status");
        return json;
//        if (code.equals("0")){
//            address = json.getString("formatted_address");
//        } else {
//            throw new RRException(json.getString("message"));
//        }
//
//        return json;
    }

    public JSONObject getDistanceBetweenTwoPoints(String lat1, String lng1, String lat2, String lng2) throws Exception {
        Map<String, String> paramsMap = new LinkedHashMap<>();
        paramsMap.put("origins", lat1 + "," + lng1);
        paramsMap.put("destinations", lat2 + "," + lng2);//
        paramsMap.put("output", "json");
        paramsMap.put("ak", appKey);

        // 调用下面的MD5方法得到最后的sn签名7de5a22212ffaa9e326444c75a58f9a0
        String sn = SnCal.getSn("/routematrix/v2/walking", paramsMap);
        String paramsStr = SnCal.toQueryString(paramsMap);
        String apiUrl = "https://api.map.baidu.com/routematrix/v2/walking?" + paramsStr;
        HttpClient client = new DefaultHttpClient();
        HttpGet httpget = new HttpGet( apiUrl);
        HttpResponse response = client.execute(httpget);
        InputStream is = response.getEntity().getContent();
        String result = SnCal.inStream2String(is);
        JSONObject json = JSONObject.parseObject(result);
        String code = json.getString("status");
        return json;
//        if (code.equals("0")){
//            address = json.getString("formatted_address");
//        } else {
//            throw new RRException(json.getString("message"));
//        }
//
//        return json;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }
}
