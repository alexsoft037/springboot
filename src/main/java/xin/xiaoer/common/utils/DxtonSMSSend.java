package xin.xiaoer.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public class DxtonSMSSend {

    public static String SMS(String postData, String postUrl) {
        try {
            //发送POST请求
            URL url = new URL(postUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setUseCaches(false);
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Length", "" + postData.length());
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(postData);
            out.flush();
            out.close();

            //获取响应状态
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("connect failed!");
                return "";
            }
            //获取响应内容体
            String line, result = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            while ((line = in.readLine()) != null) {
                result += line + "\n";
            }
            in.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return "";
    }

    public static String sendVerifyCode(String phone, String code) throws IOException {
        String PostData = "account=yidianhui&password=guoshan1104&mobile="+phone+"&content="+java.net.URLEncoder.encode("您的验证码是："+code+"。如需帮助请联系客服。","utf-8");
        String ret = DxtonSMSSend.SMS(PostData, "http://sms.106jiekou.com/utf8/sms.aspx");
        return ret;
    }

    //添加
    public static String sendActivityMessage(String phone, String content) throws IOException {
        String PostData = "account=yidianhui&password=guoshan1104&mobile="+phone+"&content="+java.net.URLEncoder.encode(content);
        String ret = DxtonSMSSend.SMS(PostData, "http://sms.106jiekou.com/utf8/sms.aspx");
        return ret;
    }

    public static String getResponseText(String code){
        Map<String, String> map = new HashMap<>();
        map.put("100", "发送成功");
        map.put("101", "验证失败");
        map.put("102", "手机号码格式不正确");
        map.put("103", "会员级别不够");
        map.put("104", "内容未审核");
        map.put("105", "内容过多");
        map.put("106", "账户余额不足");
        map.put("107", "Ip受限");
        map.put("108", "手机号码发送太频繁，请换号或隔天再发");
        map.put("109", "帐号被锁定");
        map.put("110", "手机号发送频率持续过高，黑名单屏蔽数日");
        map.put("120", "系统升级");

        return map.get(code);
    }
}
