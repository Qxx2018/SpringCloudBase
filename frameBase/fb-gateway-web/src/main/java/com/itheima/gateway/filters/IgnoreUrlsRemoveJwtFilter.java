//package com.itheima.gateway.filters;
//
//import com.itheima.gateway.config.WhiteUrlConfig;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.stereotype.Component;
//import org.springframework.util.AntPathMatcher;
//import org.springframework.util.PathMatcher;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebFilter;
//import org.springframework.web.server.WebFilterChain;
//import reactor.core.publisher.Mono;
//
//import javax.annotation.Resource;
//import java.net.URI;
//import java.util.List;
//
///**
// * 白名单路径访问时需要移除JWT请求头
// *
// * @author macro
// * @date 2020/7/24
// */
//@Component
//public class IgnoreUrlsRemoveJwtFilter implements WebFilter {
//
//    @Resource
//    private WhiteUrlConfig whiteUrlConfig;
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        ServerHttpRequest request = exchange.getRequest();
//        URI uri = request.getURI();
//        PathMatcher pathMatcher = new AntPathMatcher();
//        //白名单路径移除JWT请求头
//        List<String> ignoreUrls = whiteUrlConfig.getUrls();
//        for (String ignoreUrl : ignoreUrls) {
//            if (pathMatcher.match(ignoreUrl, uri.getPath())) {
//                request = exchange.getRequest().mutate().header("Authorization", "").build();
//                exchange = exchange.mutate().request(request).build();
//                return chain.filter(exchange);
//            }
//        }
//        return chain.filter(exchange);
//    }
//}