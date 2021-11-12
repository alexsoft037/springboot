package xin.xiaoer.common.startRun;

import org.apache.commons.lang.StringUtils;
import org.java_websocket.WebSocketImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import xin.xiaoer.common.config.WsConfig;
import xin.xiaoer.common.wss.WsServer;

/**
 * @Author jiangyujie【1512941128@qq.com】
 * @Title ApplicationRunner
 * @Description TODO
 * @Package com.wlsj.common.startRun
 * @Date 2018-06-20 9:36
 **/
@Component
@Order(value = 1)
public class XiaoerApplicationRunner implements ApplicationRunner {

    @Autowired
    private WsConfig wsConfig;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        WebSocketImpl.DEBUG = false;
        int port = 8887;
        if(StringUtils.isNotBlank(wsConfig.getPort())){
            port = Integer.parseInt(wsConfig.getPort()); // 端口
        }
        WsServer s = new WsServer(port);
//        s.start();
    }
}
