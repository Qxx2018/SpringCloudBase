package com.itheima.oauth.certification.business.impl;

import com.itheima.oauth.certification.business.service.TokenEndpointService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * oauth token端点服务实现
 * @author XinXingQian
 */
@Service
@Slf4j
public class TokenEndpointServiceImpl implements TokenEndpointService {
    /**
     * 生成Token
     *
     * @param request
     * @param response
     * @param token
     * @return
     */
    @Override
    public OAuth2AccessToken createToken(HttpServletRequest request, HttpServletResponse response, AbstractAuthenticationToken token) {
        return null;
    }
}
