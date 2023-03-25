package com.itheima.oauth.config;

import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.annotation.Resource;

/**
 * oauth服务-鉴权配置
 * @author XinXingQian
 */
@Configuration
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private final WhiteUrlConfig whiteUrlConfig;

    /**
     * Override this method to configure the {@link HttpSecurity}. Typically subclasses
     * should not invoke this method by calling super as it may override their
     * configuration. The default configuration is:
     *
     * <pre>
     * http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
     * </pre>
     *
     * @param http the {@link HttpSecurity} to modify
     * @throws Exception if an error occurs
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .and()
                .authorizeRequests()
                //允许放行的请求
                .antMatchers(ArrayUtil.toArray(whiteUrlConfig.getUrls(),String.class)).permitAll()
                //所有请求都必须认证才能访问，必须登录
                .anyRequest().authenticated()
                //开启跨域
                .and().cors()
                //关闭csrf
                .and().csrf().disable();


    }
}
