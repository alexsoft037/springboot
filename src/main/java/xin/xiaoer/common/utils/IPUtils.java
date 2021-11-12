package xin.xiaoer.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * IP地址
 * 
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2017年3月8日 下午12:57:02
 */
public class IPUtils {
	private static Logger logger = LoggerFactory.getLogger(IPUtils.class);

    private static final String[] IP_HEADER_CANDIDATES = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR" };

    /**
     * @return java.lang.String
     * @Description TODO 修改nginx后获取ip
     * @Param [request]
     * @Author XiaoDong
     **/
    public static String getIp(HttpServletRequest request) {
        try {
            for (String header : IP_HEADER_CANDIDATES) {
                String ip = request.getHeader(header);
                if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                    int index = ip.indexOf(",");
                    if (index != -1) {
                        return ip.substring(0, index);
                    }
                    return ip;
                }
            }
        }catch (Exception e){
            logger.error("IPUtils ERROR ", e);
        }
        return request.getRemoteAddr();
    }

	/**
	 * 获取IP地址 测试提交
	 *
	 * 使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
	 * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
	 */
	public static String getIpAddr(HttpServletRequest request) {
    	String ip = null;
        try {
            ip = request.getHeader("x-forwarded-for");
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } catch (Exception e) {
        	logger.error("IPUtils ERROR ", e);
        }

//        //使用代理，则获取第一个IP地址
//        if(StringUtils.isEmpty(ip) && ip.length() > 15) {
//			if(ip.indexOf(",") > 0) {
//				ip = ip.substring(0, ip.indexOf(","));
//			}
//		}

        return ip;
    }

    /**
     * @return java.lang.String
     * @Description TODO 根据ip获取城市名
     * @Param [ip]
     * @Author XiaoDong
     **/
    public static String getAddress(String ip){
        RestTemplate restTemplate = new RestTemplate();
        String s = restTemplate.getForObject("http://api.map.baidu.com/location/ip?ak=kT8O1sGE3wWgVH9vcbkhURGNd5LruAFe&ip="+ip+"&coor=bd09ll", String.class);
        if (!JSON.parseObject(s).getString("status").equals("0")){
            return "ip illegal";
        }
        JSONObject addressObject = JSON.parseObject(s).getJSONObject("content").getJSONObject("address_detail");
        String address = addressObject.getString("province")+"|"+addressObject.getString("city");
        if (!StringUtils.isBlank(addressObject.getString("street"))){
            address+="|"+addressObject.getString("street");
        }
        return address;
    }

    /**
     * @return java.lang.String
     * @Description TODO 根据ip获取经纬度
     * @Param [ip]
     * @Author XiaoDong
     **/
    public static String getPoint(String ip){
        RestTemplate restTemplate = new RestTemplate();
        String s = restTemplate.getForObject("http://api.map.baidu.com/location/ip?ak=kT8O1sGE3wWgVH9vcbkhURGNd5LruAFe&ip="+ip+"&coor=bd09ll", String.class);
        if (!JSON.parseObject(s).getString("status").equals("0")){
            return "ip illegal";
        }
        JSONObject object = JSON.parseObject(s).getJSONObject("content").getJSONObject("point");
        String xy = object.getString("x")+"|"+object.getString("y");
        return xy;
    }
	
}
