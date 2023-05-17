package com.itheima.oauth.certification.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 身份鉴权安全保护
 * <br/>开启Spring方法级安全@EnableGlobalMethodSecurity
 * @author XinXingQian
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 注入一个认证管理器，自身不实现身份认证，这一步必不可少，否则SpringBoot会自动配置一个AuthenticationManager,覆盖掉内存中的用户
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 授权码模式在浏览器地址栏发起请求来获取 code
     * .anyRequest().authenticated() 必须对该请求进行认证拦截，发现用户没有登陆的时候会弹出登陆框, 从而让用户输入用户名和密码进行登陆, 若是对该请求进行放行, 则登陆页无法弹出, 并抛出 InsufficientAuthenticationException
     * .httpBasic() 因为用户未登陆访问了受保护的资源, 所以还要开启 httpBasic 进行简单认证, 否则会抛出 AccessDeniedException 异常,
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                //不需要授权的请求
                .antMatchers( "/oauth*" ).permitAll()
                //除上面匹配任何请求都需要授权
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                //自定义认证
                //.apply()
                //关闭跨域保护
                .csrf().disable()
                //启用CORS支持
                .cors().and()
                //禁用缓存
                .headers().cacheControl().disable();

    }
}
