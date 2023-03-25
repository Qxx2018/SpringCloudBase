package com.itheima.gateway.manager;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * 鉴权管理器
 * 在WebFlux中，我们需要实现ReactiveAuthorizationManager<AuthorizationContext>类，
 * 并在check方法中进行验权限，check方法可以获取用户登录时的权限和当前请求路径，
 * 如果返回可以访问的标志Mono.just(new AuthorizationDecision(true)) 则可以访问当前地址，否则则无限访问
 * @author XinXingQian
 */
@Component
@AllArgsConstructor
@Slf4j
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    /**
     * Determines if access is granted for a specific authentication and object.
     *
     * @param authentication the Authentication to check
     * @param object         the object to check
     * @return an decision or empty Mono if no decision could be made.
     */
    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext object) {


        ServerHttpRequest request = object.getExchange().getRequest();
        //跨域的预检请求
        if (HttpMethod.OPTIONS.equals(request.getMethod())) {
            return Mono.just(new AuthorizationDecision(true));
        }
        //获取请求url
        String url = request.getPath().pathWithinApplication().value();
        //资源权限
        List<String> resourceAuthorityCodeList = new ArrayList<>();
        //TODO 根据请求地址获取对应需要的权限->需要哪些权限可以访问这个地址
        //TODO ..........
        return authentication
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(resourceAuthorityCode->{
                    //TODO 校验登入用户的权限是否满足请求地址需要的权限
                    return true;
                })
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }
}
