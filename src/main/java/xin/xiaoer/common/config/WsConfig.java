package xin.xiaoer.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Author jiangyujie【1512941128@qq.com】
 * @Title WsConfig
 * @Description TODO
 * @Package com.wlsj.common.config
 * @Date 2018-06-19 16:35
 **/
@Configuration
public class WsConfig {
    private Logger logger = LoggerFactory.getLogger(WsConfig.class);

    @Value("${ws.port}")
    private String port;

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }


}
