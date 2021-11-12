package xin.xiaoer.common.utils;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/** 
 * 说明：路径工具类
 * 创建人：FH Q313596790
 * 修改时间：2014年9月20日
 * @version
 */
public class PathUtil {

	/**
	 * 图片访问路径
	 * @param pathType
	 *            图片类型 visit-访问；save-保存
	 * @param pathCategory
	 *            图片类别，如：话题图片-topic、话题回复图片-reply、商家图片
	 * @return
	 */
	public static String getPicturePath(String pathType, String pathCategory) {
		String strResult = "";
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		StringBuffer strBuf = new StringBuffer();
		if ("visit".equals(pathType)) {
		} else if ("save".equals(pathType)) {
			String projectPath = PublicUtil.getPorjectPath().replaceAll("\\\\",
					"/");
			projectPath = splitString(projectPath, "bin/");

			strBuf.append(projectPath);
			strBuf.append("webapps/ROOT/");
		}

		strResult = strBuf.toString();

		return strResult;
	}

	public static String getRealPath(String p) {
		p = StringUtils.trimLeadingCharacter(p, '/');
		p = StringUtils.trimLeadingCharacter(p, '\\');
		p = "/" + p;

		try {
			URI uri=PathUtil.class.getResource("/").toURI();
			Path projectPath = Paths.get(uri).getParent().getParent();
			String projectName = projectPath.getName(projectPath.getNameCount() - 1).toString();
			if (p.startsWith("/" + projectName)) {
				p=p.replace(projectName, "");
			}
			return projectPath + p;
		} catch (URISyntaxException e) {
			throw new RuntimeException("解析url失败",e);
		}
	}




	public static void main(String[] args) {
		String s=getRealPath("/gongsi-weiSNS2/kkk");
		Path p=Paths.get(s);
		System.out.println(p.getName(p.getNameCount()-1));

		System.out.println(s);
	}

	private static String splitString(String str, String param) {
		String result = str;

		if (str.contains(param)) {
			int start = str.indexOf(param);
			result = str.substring(0, start);
		}

		return result;
	}
	
	/*
	 * 获取classpath1
	 */
	public static String getClasspath(){
		String path = (String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))+"../../").replaceAll("file:/", "").replaceAll("%20", " ").trim();	
		if(path.indexOf(":") != 1){
			path = File.separator + path;
		}
		return path;
	}
	
	/*
	 * 获取classpath2
	 */
	public static String getClassResources(){
		String path =  (String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))).replaceAll("file:/", "").replaceAll("%20", " ").trim();	
		if(path.indexOf(":") != 1){
			path = File.separator + path;
		}
		return path;
	}
	
	public static String PathAddress() {
		String strResult = "";
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();

		StringBuffer strBuf = new StringBuffer();

		strBuf.append(request.getScheme() + "://");
		strBuf.append(request.getServerName() + ":");
		strBuf.append(request.getServerPort() + "");

		strBuf.append(request.getContextPath() + "/");

		strResult = strBuf.toString();// +"ss/";//加入项目的名称

		return strResult;
	}
	
}
