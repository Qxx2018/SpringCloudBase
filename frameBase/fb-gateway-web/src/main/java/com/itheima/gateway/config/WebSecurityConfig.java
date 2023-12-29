package com.itheima.gateway.config;

import cn.hutool.core.util.ArrayUtil;
import com.itheima.gateway.component.RestAuthenticationEntryPoint;
import com.itheima.gateway.component.RestfulAccessDeniedHandler;
import com.itheima.gateway.manager.AuthorizationManager;
import com.itheima.sys.core.constants.AuthConstant;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

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
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Resource
    private final RestfulAccessDeniedHandler restfulAccessDeniedHandler;


    @Resource
    private final WhiteUrlConfig whiteUrlConfig;

    @Resource
    private final AuthorizationManager authorizationManager;


    @Bean
    SecurityWebFilterChain webFluxSecurityWebFilterChain(ServerHttpSecurity http) {
        //http.oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter());
        //自定义处理JWT请求头过期或签名错误的结果
        //http.oauth2ResourceServer().authenticationEntryPoint(restAuthenticationEntryPoint);
        //对白名单路径，直接移除JWT请求头
        //http.addFilterBefore(ignoreUrlsRemoveJwtFilter, SecurityWebFiltersOrder.AUTHENTICATION);
        http.authorizeExchange()
                //无需权限过滤的请求路径
                .pathMatchers(ArrayUtil.toArray(whiteUrlConfig.getUrls(),String.class)).permitAll()
                //无需权限过滤的请求方式
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                //其他任何请求都需要鉴权管理
                .anyExchange().access(authorizationManager)
                .and().exceptionHandling()
                //处理未授权
                .accessDeniedHandler(restfulAccessDeniedHandler)
                //处理未认证
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                //开启跨域
                .and().cors()
                //使用的是JWT，禁用csrf
                .and().csrf().disable();
        return http.build();
    }
    @Bean
    public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix(AuthConstant.AUTHORITY_PREFIX);
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(AuthConstant.JWT_AUTHORITIES_KEY);
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }

}
