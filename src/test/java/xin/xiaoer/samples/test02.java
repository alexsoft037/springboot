package xin.xiaoer.samples;

//演示get、post请求如何算sn，算得sn如何使用
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import xin.xiaoer.common.utils.Constant;

import javax.print.URIException;

@SuppressWarnings("deprecation")
public class test02 {
    public static void main(String[] args) throws Exception {
        test02 test02 = new test02();
        test02.testGet();
//        test02.testPost();
    }

    @SuppressWarnings("all")
    public void testGet() throws Exception {
        /**
         * 以http://api.map.baidu.com/geocoder/v2/?address=百度大厦&output=json&ak=yourak为例
         * ak设置了sn校验不能直接使用必须在url最后附上sn值，get请求计算sn跟url中参数对出现顺序有关，需按序填充paramsMap，
         * post请求是按字母序填充，具体参照testPost()
         */
        Map paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put("address", "百度大厦");
        paramsMap.put("output", "json");
        paramsMap.put("ak", Constant.BaiduAK);

        // 调用下面的toQueryString方法，对paramsMap内所有value作utf8编码
        String paramsStr = toQueryString(paramsMap);

        // 对paramsStr前面拼接上/geocoder/v2/?，后面直接拼接yoursk
        String wholeStr = new String("/geocoder/v2/?" + paramsStr + Constant.BaiduSK);

        // 对上面wholeStr再作utf8编码
        String tempStr = URLEncoder.encode(wholeStr, "UTF-8");

        // 调用下面的MD5方法得到sn签名值d
        String sn = MD5(tempStr);

        // 算得sn后发送get请求
        HttpClient client = new DefaultHttpClient();
        HttpGet httpget = new HttpGet( "http://api.map.baidu.com/geocoder/v2/?" + paramsStr + "&sn=" + sn);
        HttpResponse response = client.execute(httpget);
        InputStream is = response.getEntity().getContent();
        String result = inStream2String(is);
        // 打印响应内容
        System.out.println(result);
    }

    public void testPost() throws Exception {
        /**
         * 以http://api.map.baidu.com/geodata/v3/geotable/create创建表为例
         */
        LinkedHashMap<String, String> paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put("geotype", "1");
        paramsMap.put("ak", Constant.BaiduAK);
        paramsMap.put("name", "geotable80");
        paramsMap.put("is_published", "1");

        // post请求是按字母序填充，对上面的paramsMap按key的字母序排列
        Map<String, String> treeMap = new TreeMap<String, String>(paramsMap);
        String paramsStr = toQueryString(treeMap);

        String wholeStr = new String("/geodata/v3/geotable/create?" + paramsStr
                + Constant.BaiduSK);
        String tempStr = URLEncoder.encode(wholeStr, "UTF-8");
        // 调用下面的MD5方法得到sn签名值
        String sn = MD5(tempStr);

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(
                "http://api.map.baidu.com/geodata/v3/geotable/create");
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("geotype", "1"));
        params.add(new BasicNameValuePair("ak", Constant.BaiduAK));
        params.add(new BasicNameValuePair("name", "geotable80"));
        params.add(new BasicNameValuePair("is_published", "1"));
        params.add(new BasicNameValuePair("sn", sn));
        HttpEntity formEntity = new UrlEncodedFormEntity(params);
        post.setEntity(formEntity);
        HttpResponse response = client.execute(post);
        InputStream is = response.getEntity().getContent();
        String result = inStream2String(is);
        // 打印响应内容
        System.out.println(result);
    }

    // 对Map内所有value作utf8编码，拼接返回结果
    static public String toQueryString(Map<?, ?> data) throws UnsupportedEncodingException {
        StringBuffer queryString = new StringBuffer();
        for (Entry<?, ?> pair : data.entrySet()) {
            queryString.append(pair.getKey() + "=");
            String ss[] = pair.getValue().toString().split(",");
            if (ss.length>1){
                for (String s:ss){
                    queryString.append(URLEncoder.encode(s,"UTF-8") + ",");
                }
                queryString.deleteCharAt(queryString.length()-1);
                queryString.append("&");
            }
            else {
                queryString.append(URLEncoder.encode((String) pair.getValue(),
                        "UTF-8") + "&");
            }
        }
        if (queryString.length() > 0) {
            queryString.deleteCharAt(queryString.length() - 1);
        }
        return queryString.toString();
    }
    // MD5计算方法，调用了MessageDigest库函数，并把byte数组结果转换成16进制
    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                        .substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    // 将输入流转换成字符串
    private static String inStream2String(InputStream is) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len = -1;
        while ((len = is.read(buf)) != -1) {
            baos.write(buf, 0, len);
        }
        return new String(baos.toByteArray(), "UTF-8");
    }
}