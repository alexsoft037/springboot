package xin.xiaoer.common.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;

/**
 * 项目名称：
 *
 * @author:fh qq313596790[青苔]
 * 修改日期：2015/11/2
 */
public class Const {
	public static final String APPLICATION_BASE_PATH = "basepath";
	public static final String SQ_NAME = "sqname";
	public static final String STREET_NAME="street_name";
	public static final Map<String, String> MENUS = new HashMap<String,String>();
	public static final Map<String, String> SQNAMES = new HashMap<String,String>();
	public static final String SQ_BINGDING_ID = "sqBingdingId";
	public static final Integer PAGE_SIZE = 10;
	public static final String WEIXIN_USER = "weixin_user";
	public static final String SESSION_USERROLEMENU = "sessionUserRoleMenu";
	public static final String SESSION_USERROLEFUNCS = "sessionUserRoleFuncs";
	public static final String CURRENT_MENUID = "currentMenuId";
	public static final String WEIXIN_BINDING="weixin_binding";
	public static final String SESSION_SECURITY_CODE = "sessionSecCode";//验证码
	public static final String SESSION_USER = "sessionUser";            //session用的用户
	public static final String SESSION_ROLE_RIGHTS = "sessionRoleRights";
	public static final String sSESSION_ROLE_RIGHTS = "sessionRoleRights";
	public static final String SESSION_menuList = "menuList";            //当前菜单
	public static final String SESSION_allmenuList = "allmenuList";        //全部菜单
	public static final String SESSION_rightMenuList = "rightMenuList";
	public static final String SESSION_QX = "QX";
	public static final String SESSION_userpds = "userpds";
	public static final String SESSION_USERROL = "USERROL";                //用户对象
	public static final String SESSION_USERNAME = "USERNAME";            //用户名
	public static final String SESSION_KEY_SYS_MSG = "SysMsg";            //
	public static final String SYS_MSG_ITEM_TPL = "<div class='alert alert-%s'>%s</div>";
	public static final String TRUE = "T";
	public static final String FALSE = "F";
	public static final String LOGIN = "/login_form.do";                //登录地址
	public static final String SYSNAME = "admin/config/SYSNAME.txt";    //系统名称路径
	public static final String PAGE = "admin/config/PAGE.txt";            //分页条数配置路径
	public static final String EMAIL = "admin/config/EMAIL.txt";        //邮箱服务器配置路径
	public static final String SMS1 = "admin/config/SMS1.txt";            //短信账户配置路径1
	public static final String SMS2 = "admin/config/SMS2.txt";            //短信账户配置路径2
	public static final String FWATERM = "admin/config/FWATERM.txt";    //文字水印配置路径
	public static final String IWATERM = "admin/config/IWATERM.txt";    //图片水印配置路径
	public static final String WEIXIN = "admin/config/WEIXIN.txt";    //微信配置路径
	public static final String WEBSOCKET = "admin/config/WEBSOCKET.txt";//WEBSOCKET配置路径
	public static final String FILEPATH_STATIC = "static/";
	public static final String FILEPATH_UPLOAD = "upload/";    //图片上传路径
	public static final String FILEPATH_UPLOAD_THUMBNAIL = FILEPATH_UPLOAD + "thumbnail/";   //文件上传路径
	public static final String FILEPATHTWODIMENSIONCODE = "resource/twoDimensionCode/"; //二维码存放路径
	public static final String NO_INTERCEPTOR_PATH = ".*/((login)|(logout)|(code)|(app)|(weixin)|(static)|(main)|(websocket)).*";    //不对匹配该值的访问路径拦截（正则）
	public static final Long ACCESS_TOCKEN_CACHE_SECONDS = 7000L;
	public static final String SERVLET_CONTEXT = "servletContext";
	public static final String WX_COUNTINFO_ASY_JD = "weixin_countinfo_asy_jd";
	public static ApplicationContext WEB_APP_CONTEXT = null; //该值会在web容器启动时由WebAppContextListener初始化
	public static String DOMAIN = null; //该值会在web容器启动时由WebAppContextListener初始化
	public static final String CLIENT_TOCKEN="tocken4weixin";
	public static final String TEMP_CLIENT_TOCKEN = "temp_tocken4session";
	
	public static final String SESSION_MSG = "sessionMsg";  
	/**
	 * APP Constants
	 */
	//app注册接口_请求协议参数)
	public static final String[] APP_REGISTERED_PARAM_ARRAY = new String[]{"countries", "uname", "passwd", "title", "full_name", "company_name", "countries_code", "area_code", "telephone", "mobile"};
	public static final String[] APP_REGISTERED_VALUE_ARRAY = new String[]{"国籍", "邮箱帐号", "密码", "称谓", "名称", "公司名称", "国家编号", "区号", "电话", "手机号"};

	//app根据用户名获取会员信息接口_请求协议中的参数
	public static final String[] APP_GETAPPUSER_PARAM_ARRAY = new String[]{"USERNAME"};
	public static final String[] APP_GETAPPUSER_VALUE_ARRAY = new String[]{"用户名"};



}
