package com.itheima.oauth.certification.business.service;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * oauth token端点服务
 * @author XinXingQian
 */
public interface TokenEndpointService {
    /**
     * 生成Token
     * @param request
     * @param response
     * @param token
     * @return
     */
    OAuth2AccessToken createToken(HttpServletRequest request, HttpServletResponse response,
                                  AbstractAuthenticationToken token);

}
