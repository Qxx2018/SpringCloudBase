package com.itheima.gateway.component;

import cn.hutool.core.util.StrUtil;
import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import reactor.core.publisher.Mono;

/**
 * 响应包装类
 * @author XinXingQian
 */
public class HttpResponseDecorator extends ServerHttpResponseDecorator {

    private String proxyUrl;

    private ServerHttpRequest request;

    /**
     * 构造函数
     *
     * @param delegate
     */
    public HttpResponseDecorator(ServerHttpRequest request, ServerHttpResponse delegate, String proxyUrl) {
        super(delegate);
        this.request = request;
        this.proxyUrl = proxyUrl;
    }

    @Override
    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
        HttpStatus status = this.getStatusCode();
        if (HttpStatus.FOUND.equals(status)) {
            String domain = "";
            if (StrUtil.isBlank(proxyUrl)) {
                domain = request.getURI().getScheme() + "://" + request.getURI().getAuthority() + "/oauth-certification";
            } else {
                domain = proxyUrl + "/oauth-certification";
            }
            String location = getHeaders().getFirst("Location");
            String replaceLocation = location.replaceAll("^((ht|f)tps?):\\/\\/(\\d{1,3}.){3}\\d{1,3}(:\\d+)?", domain);
            if (location.contains("code=")) {
                //TODO ?
            } else {
                getHeaders().set("Location", replaceLocation);
            }
        }
        this.getStatusCode();
        return super.writeWith(body);
    }
}


