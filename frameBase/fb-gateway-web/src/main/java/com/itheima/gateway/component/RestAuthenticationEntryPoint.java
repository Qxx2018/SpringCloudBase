package com.itheima.gateway.component;


import com.alibaba.fastjson.JSONObject;
import com.itheima.sys.core.constants.RequestConstant;
import com.itheima.sys.core.enums.BusinessExceptionEnums;
import com.itheima.sys.core.resp.Rsp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * 自定义返回结果：没有登录或token无效、过期时
 *
 * @author macro
 * @date 2020/6/18
 */
@Component
@Slf4j
public class RestAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
        ServerHttpResponse response = exchange.getResponse();
        String token = response.getHeaders().getFirst(RequestConstant.AUTHORIZATION);
        log.info("token无效或过期的自定义响应 ==================== {}", token);
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
        response.getHeaders().set("Access-Control-Allow-Origin","*");
        response.getHeaders().set("Cache-Control","no-cache");
        String body= JSONObject.toJSONString(
                Rsp.error(BusinessExceptionEnums.TOKEN_INVALID_OR_EXPIRED)
        );
        DataBuffer buffer =  response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer)).doOnError(error-> DataBufferUtils.release(buffer));
    }
}
