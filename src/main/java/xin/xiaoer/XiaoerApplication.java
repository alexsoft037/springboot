package xin.xiaoer;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;

@EnableEurekaClient
@SpringBootApplication
// mapper 接口类扫描包配置
//会使过滤器失效
@ServletComponentScan
@MapperScan({"xin.xiaoer.dao","xin.xiaoer.modules.*.dao"})
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)//解决jmx重复注册bean的问题
@Import(FdfsClientConfig.class)//通过注解配置FastDFS连接池
public class XiaoerApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		builder.sources(this.getClass());
		return super.configure(builder);
	}

	public static void main(String[] args) {
		SpringApplication.run(XiaoerApplication.class, args);
	}
}
