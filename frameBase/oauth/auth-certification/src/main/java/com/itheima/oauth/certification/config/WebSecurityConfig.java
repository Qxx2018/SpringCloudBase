package com.itheima.oauth.certification.config;

import com.itheima.oauth.certification.service.impl.SysAccountServiceImpl;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * 身份鉴权安全保护
 * <br/>开启Spring方法级安全@EnableGlobalMethodSecurity
 * @author XinXingQian
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private SysAccountServiceImpl sysAccountService;
    /**
     * 加密
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        //BCryptPasswordEncoder是Spring Security中的一个加密方法。BCryptPasswordEncoder方法采用了SHA-256+随机盐+密钥对密码进行加密
        return new BCryptPasswordEncoder(
                BCryptPasswordEncoder.BCryptVersion.$2Y,
                16);
    }

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

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //https://blog.csdn.net/weixin_44802953/article/details/109154822

        //内存中的身份验证-测试使用
//        auth
//                .inMemoryAuthentication()
//                //密码需要设置编码器,如BcryptPasswordEncoder、Pbkdf2PasswordEncoder、ScryptPasswordEncoder等，不限于上面所展示的
//                .passwordEncoder(passwordEncoder())
//                .withUser("test")
//                .password(passwordEncoder().encode("123456"))
//                //可定义用户角色roles，也可定义权限authorities。在进行赋值时，权限通常实在角色值的基础上添加ROLE_前缀。如authorities("ROLE_common")和roles("common")是等效的
//                //定义角色
//                .roles("USER")
//                //定义权限
//                .authorities("ROLE_common");
        /**
         * 需要注意的是，在实际开发过程中，
         * 用户都是在页面注册和登陆时进行认证管理的，
         * 而非在程序内部使用内存管理的方式手动控制注册用户，
         * 所以 上面的内存身份认证方式无法用于实际中，我们只是初学拿来练手哦
         */
        //JDBC Authentication（JDBC身份认证）
        /**
         * 对于用户流量较大的项目，
         * 频繁的使用JDBC身份认证查询不仅操作麻烦，
         * 而且会降低网站相应速度。
         * 对于一个完善的项目来讲，
         * 如果某些业务已经实现类用户信息查询的服务，
         * 就没不要再使用JDBC身份认证了
         */
        //UserDetailsService身份认证
        auth.userDetailsService(sysAccountService).passwordEncoder(passwordEncoder());
    }
}
