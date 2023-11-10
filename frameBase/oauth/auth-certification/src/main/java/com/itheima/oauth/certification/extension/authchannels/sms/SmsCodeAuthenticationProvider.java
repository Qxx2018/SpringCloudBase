package com.itheima.oauth.certification.extension.authchannels.sms;

import com.itheima.common.enums.BusinessExceptionEnums;
import com.itheima.common.exception.BusinessException;
import com.itheima.oauth.certification.business.impl.LoginCertificationServiceImpl;
import com.itheima.oauth.certification.business.service.ValidateCodeService;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.HashSet;

/**
 * 手机短信验证码认证授权提供者----自定义Provider
 * @author XinXingQian
 */
@Data
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private ValidateCodeService validateCodeService;
    private UserDetailsService userDetailsService;

    /**
     * 使用相同的合约执行认证
     * {@link AuthenticationManager#authenticate(Authentication)}
     * @param认证认证请求对象。
     * @return 返回一个完全认证的对象，包括凭据。可能会返回
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //接收传进来的手机号和手机短信验证码----进行校验
        SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken) authentication;
        String mobile = (String) authenticationToken.getPrincipal();
        String code = (String) authenticationToken.getCredentials();
        if(!validateCodeService.validate(code,mobile)) {
            throw new BusinessException(BusinessExceptionEnums.SMS_ERROR);
        }
        //Core interface which loads user-specific data.
        //加载用户特定数据的核心接口 登入校验成功后返回用户权限资源
        UserDetails userDetails = ((LoginCertificationServiceImpl)userDetailsService).loadUserByPhone(mobile);
        SmsCodeAuthenticationToken result = new SmsCodeAuthenticationToken(userDetails, authentication.getCredentials(), new HashSet<>());
        result.setDetails(authentication.getDetails());
        return result;
    }

    /**
     * Returns <code>true</code> if this <Code>AuthenticationProvider</code> supports the
     * indicated <Code>Authentication</code> object.
     * <p>
     * Returning <code>true</code> does not guarantee an
     * <code>AuthenticationProvider</code> will be able to authenticate the presented
     * instance of the <code>Authentication</code> class. It simply indicates it can
     * support closer evaluation of it. An <code>AuthenticationProvider</code> can still
     * return <code>null</code> from the {@link #authenticate(Authentication)} method to
     * indicate another <code>AuthenticationProvider</code> should be tried.
     * </p>
     * <p>
     * Selection of an <code>AuthenticationProvider</code> capable of performing
     * authentication is conducted at runtime the <code>ProviderManager</code>.
     * </p>
     *
     * @param authentication
     * @return <code>true</code> if the implementation can more closely evaluate the
     * <code>Authentication</code> class presented
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
