package xin.xiaoer.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密
 * @author Maclaine
 *
 */
public class Encryption {
	public static final String MD5 = "MD5";

	public static byte[] EncryptionStrBytes(String str, String algorithm) {
		byte[] bytes = null;
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			md.update(str.getBytes());
			bytes = md.digest();
		} catch (NoSuchAlgorithmException e) {
			System.out.println("加密算法: " + algorithm + " 不存在: ");
		}
		return null == bytes ? null : bytes;
	}

	/**
	 * 把字节数组转化成字符串返回
	 * 
	 * @param bytes
	 * @return
	 */
	public static String BytesConvertToHexString(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for (byte aByte : bytes) {
			String s = Integer.toHexString(0xff & aByte);
			if (s.length() == 1) {
				sb.append("0" + s);
			} else {
				sb.append(s);
			}
		}
		return sb.toString();
	}

	/**
	 * 采用加密算法加密字符串数据
	 * 
	 * @param str
	 *            需要加密的数据
	 * @param algorithm
	 *            采用的加密算法
	 * @return 字节数据
	 */
	public static String EncryptionStr(String str, String algorithm) {
		byte[] bytes = EncryptionStrBytes(str, algorithm);
		return BytesConvertToHexString(bytes);
	}

	/**
	 * MD5加密
	 * 
	 * @param str
	 *            需要加密的数据
	 * @return
	 */
	public static String MD5EncryptionStr(String str) {
		return EncryptionStr(str, MD5);
	}
}
