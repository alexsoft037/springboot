package xin.xiaoer.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

public class SnCal {

	/**
	 * 计算SN
	 * @param url 不需要带问号.eg:/location/ip
	 * @param paramsMap Map参数
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getSn(String url, Map<String, String> paramsMap) throws UnsupportedEncodingException {
		String paramsStr = toQueryString(paramsMap);
		String wholeStr = new String(url + "?" + paramsStr + Constant.BaiduSK);
		String tempStr = URLEncoder.encode(wholeStr, "UTF-8");
		return MD5(tempStr);
	}

	// 对Map内所有value作utf8编码，拼接返回结果
	public static String toQueryString(Map<?, ?> data)
			throws UnsupportedEncodingException {
		StringBuffer queryString = new StringBuffer();
		for (Entry<?, ?> pair : data.entrySet()) {
			queryString.append(pair.getKey() + "=");
			queryString.append(URLEncoder.encode((String) pair.getValue(),
					"UTF-8") + "&");
		}
		if (queryString.length() > 0) {
			queryString.deleteCharAt(queryString.length() - 1);
		}
		return queryString.toString();
	}

	// 来自stackoverflow的MD5计算方法，调用了MessageDigest库函数，并把byte数组结果转换成16进制
	static private String MD5(String md5) {
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
	public static String inStream2String(InputStream is) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int len = -1;
		while ((len = is.read(buf)) != -1) {
			baos.write(buf, 0, len);
		}
		return new String(baos.toByteArray(), "UTF-8");
	}
}
