package com.itheima.oauth.certification.extension.authchannels.sms;

import cn.hutool.core.lang.Assert;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 手机验证码登录----自定义token
 * @author XinXingQian
 */
public class SmsCodeAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 8306714603537224208L;

    // ~ Instance fields
    // ================================================================================================

    private final Object principal;
    /**
     * 主体身份的凭证
     */
    private Object credentials;

    // ~ Constructors
    // ===================================================================================================

    /**
     * 认证未通过的初始化方法
     */
    public SmsCodeAuthenticationToken(Object principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        this.setAuthenticated(false);
    }
    /**
     * 认证通过的初始化方法
     */
    public SmsCodeAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    // ~ Methods
    // ========================================================================================================


    /**
    * 证明委托人正确的凭据。这通常是一个密码，
    * 但可以是与<code>AuthenticationManager</code>相关的任何内容。调用者
    *将填充凭据。
    ＊
    * @return 返回证明<code>主体身份的凭证</code>
    */
    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    /**
    *  被认证主体的身份。在身份验证的情况下
    *  请求用户名和密码，这将是用户名。电话是
    *  期望填充身份验证请求的主体。
    *  < p >
    *  <tt>AuthenticationManager</tt>实现通常会返回一个
    *  <tt>认证</tt>包含更丰富的信息作为主体供
    *  应用程序。许多身份验证提供程序将创建一个
    *  {@code UserDetails}对象作为主体。
     * @return 返回被认证的<code>主体或被认证的</code
    * 认证后的主体。
    */
    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        Assert.isTrue(!authenticated,"Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

    /**
     * Checks the {@code credentials}, {@code principal} and {@code details} objects,
     * invoking the {@code eraseCredentials} method on any which implement
     * {@link CredentialsContainer}.
     */
    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }
}
