package xin.xiaoer.common.baiduTts;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import static xin.xiaoer.common.baiduTts.AipSpeech.APIKey;
import static xin.xiaoer.common.baiduTts.AipSpeech.SecretKey;

/**
 * created by Casey 2019/2/15
 */
public final class TtsUtil {

    public static int i = 0;
    public static TtsUtil read(String text) throws IOException, DemoException {
        // 发音人选择, 0为普通女声，1为普通男生，3为情感合成-度逍遥，4为情感合成-度丫丫，默认为普通女声
        final int per = 0;
        // 语速，取值0-15，默认为5中语速
         final int spd = 5;
        // 音调，取值0-15，默认为5中语调
         final int pit = 5;
        // 音量，取值0-9，默认为5中音量
         final int vol = 5;

        // 下载的文件格式, 3：mp3(default) 4： pcm-16k 5： pcm-8k 6. wav
         final int aue = 3;

         final String url = "http://tsn.baidu.com/text2audio"; // 可以使用https

         String cuid = "1234567JAVA";
        TokenHolder holder = new TokenHolder(APIKey, SecretKey, TokenHolder.ASR_SCOPE);
        holder.refresh();
        String token = holder.getToken();

        // 此处2次urlencode， 确保特殊字符被正确编码
        String params = "tex=" + ConnUtil.urlEncode(ConnUtil.urlEncode(text));
        params += "&per=" + per;
        params += "&spd=" + spd;
        params += "&pit=" + pit;
        params += "&vol=" + vol;
        params += "&cuid=" + cuid;
        params += "&tok=" + token;
        params += "&aue=" + aue;
        params += "&lan=zh&ctp=1";
        System.out.println(url + "?" + params); // 反馈请带上此url，浏览器上可以测试
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setConnectTimeout(5000);
        PrintWriter printWriter = new PrintWriter(conn.getOutputStream());
        printWriter.write(params);
        printWriter.close();
        String contentType = conn.getContentType();
        if (contentType.contains("audio/")) {
            byte[] bytes = ConnUtil.getResponseBytes(conn);
            String format = getFormat(aue);
            File file = new File(i+"result." + format); // 打开mp3文件即可播放
            i++;
            // System.out.println( file.getAbsolutePath());
            FileOutputStream os = new FileOutputStream(file);
            os.write(bytes);
            os.close();
            System.out.println("audio file write to " + file.getAbsolutePath());
        } else {
            System.err.println("ERROR: content-type= " + contentType);
            String res = ConnUtil.getResponseString(conn);
            System.err.println(res);
        }
        return null;
    }

    // 下载的文件格式, 3：mp3(default) 4： pcm-16k 5： pcm-8k 6. wav
    private static String getFormat(int aue) {
        String[] formats = {"mp3", "pcm", "pcm", "wav"};
        return formats[aue - 3];
    }

    public static void main(String[] args) {
        System.out.println(new Date().toString());
    }
}
