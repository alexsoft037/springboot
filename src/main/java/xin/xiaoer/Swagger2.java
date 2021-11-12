package xin.xiaoer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author chenyi
 * @date 2017/12/28
 */
/*
 *
 * Swagger2配置类
 * 在与spring boot集成时，放在与Application.java同级的目录下。
 * 通过@Configuration注解，让Spring来加载该类配置。
 * 再通过@EnableSwagger2注解来启用Swagger2。
 */
@Configuration
@EnableSwagger2
//新加
//@Profile("dev")
public class Swagger2 extends WebMvcConfigurerAdapter {

    //新加
    @Value("${swagger.show}")
    private boolean swaggerShow;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new AuthorizationInterceptor())
//                .addPathPatterns("/website/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (this.swaggerShow) {
            registry.addResourceHandler("swagger-ui.html")
                    .addResourceLocations("classpath:/META-INF/resources/");
            registry.addResourceHandler("/webjars*")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/");
        }
    }

    /*
    *
    * 创建API应用
    * apiInfo() 增加API相关信息
    * 通过select()函数返回一个ApiSelectorBuilder实例,用来控制哪些接口暴露给Swagger来展现，
    * 本例采用指定扫描的包路径来定义指定要建立API的目录。
    */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("xin.xiaoer.modules.website.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /*
    *
    * 创建该API的基本信息（这些基本信息会展现在文档页面中）
    * 访问地址：http://项目实际地址/swagger-ui.html
    */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Xiaoer Apis")
                .description("项目演示地址：http://srkj.guoshanchina.com")
                .termsOfServiceUrl("http://srkj.guoshanchina.com")
                .contact("xiaoer")
                .version("1.0")
                .build();
    }

}