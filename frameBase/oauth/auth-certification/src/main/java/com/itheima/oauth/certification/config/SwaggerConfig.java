package com.itheima.oauth.certification.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * http://localhost:9803/swagger-ui/index.html#/
 * @author XinXingQian
 */
@Configuration
@EnableSwagger2
@EnableOpenApi
@Profile({"dev","test"})
public class SwaggerConfig {
    /**
     * 配置 Swagger 的 Docket 的 Bean 实例
     * @return
     */
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                //配置 Swagger ApiInfo 基本信息
                .apiInfo(apiInfo())
                //启动
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 配置 Swagger信息=apiInfo
     */
    private ApiInfo apiInfo(){
        //作者信息
        Contact contact = new Contact("QianXinXing","","");

        return new ApiInfo("Oauth2 AuthorizationServer API",
                "Oauth2 认证服务 接口文档。",
                "v1.0",
                "",
                contact,
                "",
                "",
                new ArrayList());
    }
}

