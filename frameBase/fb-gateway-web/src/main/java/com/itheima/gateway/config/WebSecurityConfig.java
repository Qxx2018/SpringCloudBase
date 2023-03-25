package com.itheima.gateway.config;

import cn.hutool.core.util.ArrayUtil;
import com.itheima.gateway.manager.AuthorizationManager;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import javax.annotation.Resource;

/**
 * 身份权限配置
 * 使用WebFlux的Security配置类
 * @author XinXingQian
 */
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@AllArgsConstructor
public class WebSecurityConfig {

    @Resource
    private final WhiteUrlConfig whiteUrlConfig;

    @Resource
    private final AuthorizationManager authorizationManager;


    @Bean
    SecurityWebFilterChain webFluxSecurityWebFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange()
                //无需权限过滤的请求路径
                .pathMatchers(ArrayUtil.toArray(whiteUrlConfig.getUrls(),String.class)).permitAll()
                //无需权限过滤的请求方式
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .anyExchange().access(authorizationManager)
                //开启跨域
                .and().cors()
                //使用的是JWT，禁用csrf
                .and().csrf().disable();
        return http.build();


    }

}
