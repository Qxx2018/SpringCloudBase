package com.itheima.oauth.certification.extension.authchannels.sms;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 手机验证码授权模式----自定义授权模式
 * @author XinXingQian
 */
public class SmsCodeTokenGranter extends AbstractTokenGranter {
    /**
     * 声明授权者 SmsCodeTokenGranter 支持授权模式 sms_code
     * 根据接口传值 grant_type = sms_code 的值匹配到此授权者
     */
    private static final String GRANT_TYPE = "sms_code";

    private final AuthenticationManager authenticationManager;

    public SmsCodeTokenGranter(
            AuthenticationManager authenticationManager,
            AuthorizationServerTokenServices tokenServices,
            ClientDetailsService clientDetailsService,
            OAuth2RequestFactory requestFactory
    )
    {
        this(authenticationManager, tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
    }

    protected SmsCodeTokenGranter(
            AuthenticationManager authenticationManager,
            AuthorizationServerTokenServices tokenServices,
            ClientDetailsService clientDetailsService,
            OAuth2RequestFactory requestFactory,
            String grantType
    )
    {
        super(tokenServices,clientDetailsService,requestFactory,grantType);
        this.authenticationManager = authenticationManager;
    }




    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = new LinkedHashMap<String, String>(tokenRequest.getRequestParameters());
        //接收传入的参数
        //手机号
        String mobile = parameters.get("mobile");
        //短信验证码
        String code = parameters.get("code");
        //防止下游密码泄露
        parameters.remove("code");
        //创建SmsCodeAuthenticationToken实例----认证未通过的初始化方法
        Authentication userAuth = new SmsCodeAuthenticationToken(mobile,code);
        //把用户传入的参数交给自定义的Token
        ((AbstractAuthenticationToken) userAuth).setDetails(parameters);
        try {
            userAuth = authenticationManager.authenticate(userAuth);
        }
        catch (AccountStatusException | BadCredentialsException exception) {
            throw new InvalidGrantException(exception.getMessage());
        }
        if (Objects.isNull(userAuth) || !userAuth.isAuthenticated()) {
            throw new InvalidGrantException("Could not authenticate user: " + mobile);
        }
        OAuth2Request storedOAuth2Request = this.getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(storedOAuth2Request, userAuth);

    }
}
