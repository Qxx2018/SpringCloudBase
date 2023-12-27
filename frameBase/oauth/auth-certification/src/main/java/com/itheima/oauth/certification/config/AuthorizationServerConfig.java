package com.itheima.oauth.certification.config;

import com.itheima.oauth.certification.business.service.LoginCertificationService;
import com.itheima.oauth.certification.extension.authchannels.sms.SmsCodeTokenGranter;
import com.itheima.oauth.certification.service.impl.JdbcClientDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.springframework.security.oauth2.provider.implicit.ImplicitTokenGranter;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 认证服务配置
 * @author XinXingQian
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Resource
    private DataSource dataSource;
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private JksConfig jksConfig;
    @Resource
    private TokenEnhancer tokenEnhancer;
    @Resource
    private LoginCertificationService loginCertificationService;
    /**
     * 令牌访问端点的安全策略
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .passwordEncoder(passwordEncoder)
                //要是让/oauth/token支持client_id和client_secret做登陆认证
                //如果开启了allowFormAuthenticationForClients，那么就在BasicAuthenticationFilter之前,添加ClientCredentialsTokenEndpointFilter,使用ClientDetailsUserDetailsService来进行登陆认证
                .allowFormAuthenticationForClients()
                // /oauth/check_token公开
                .checkTokenAccess("permitAll()")
                // /oauth/token_key公开
                .tokenKeyAccess("isAuthenticated()");
    }

    /**
     * 配置客户端详细信息服务
     * OAuth2客户端
     * 用来配置客户端详情服务(ClientDetailsService), 客户端详情信息在这里进行初始化
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        JdbcClientDetailsServiceImpl jdbcClientDetailsService = new JdbcClientDetailsServiceImpl(dataSource);
        //TODO 可以自定义客户端查询的SQL（如果修改了字段）
        clients.withClientDetails(jdbcClientDetailsService);
    }

    /**
     * 令牌访问端点
     * 配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //Token 增强
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> tokenEnhancers = new ArrayList<>();
        tokenEnhancers.add(tokenEnhancer);
        tokenEnhancers.add(jwtAccessTokenConverter());
        tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);
        //token存储模式设定 默认为InMemoryTokenStore模式存储到内存中
        endpoints.tokenStore(jwtTokenStore());
        //添加授权模式
        CompositeTokenGranter compositeTokenGranter = new CompositeTokenGranter(
                this.getTokenGranter(
                        endpoints.getTokenServices(),
                        endpoints.getAuthorizationCodeServices(),
                        endpoints.getClientDetailsService(),
                        endpoints.getOAuth2RequestFactory()
                )
        );
        endpoints
                // 端口映射
                //.pathMapping("/oauth/token","/login/authorizationCode")
                .authenticationManager(authenticationManager)
                //jwt增强，自定义的加密算法对token签名
                .accessTokenConverter(jwtAccessTokenConverter())
                //JWT 添加额外信息
                .tokenEnhancer(tokenEnhancerChain)
                //授权模式
                .tokenGranter(compositeTokenGranter)
                .userDetailsService(loginCertificationService)
                //refresh token 重复使用
                .reuseRefreshTokens(false)
        ;
    }

    /**
     * 生成需要的授权模式
     *
     * @param authorizationCodeServices
     * @param clientDetailsService
     * @param requestFactory
     * @param tokenServices
     * @return {@link List<TokenGranter>}
     */
    private List<TokenGranter> getTokenGranter(
            AuthorizationServerTokenServices tokenServices,
            AuthorizationCodeServices authorizationCodeServices,
            ClientDetailsService clientDetailsService,
            OAuth2RequestFactory requestFactory)
    {
        List<TokenGranter> tokenGranters = new ArrayList<>(
                this.getDefaultTokenGranter(
                        tokenServices,
                        authorizationCodeServices,
                        clientDetailsService,
                        requestFactory)
        );
        //TODO 其他自定义的授权模式
        //添加手机短信验证码授权模式的授权者
        if (Objects.nonNull(authenticationManager)) {
            tokenGranters.add(
                    new SmsCodeTokenGranter(authenticationManager, tokenServices, clientDetailsService, requestFactory));
        }
        return tokenGranters;
    }
    /**
     * 授权模式:授权码模式+刷新令牌模式+隐式授权模式+客户端模式+密码模式
     *
     * @param authorizationCodeServices
     * @param clientDetailsService
     * @param requestFactory
     * @param tokenServices
     * @return {@link List<TokenGranter>}
     */
    private List<TokenGranter> getDefaultTokenGranter(
            AuthorizationServerTokenServices tokenServices,
            AuthorizationCodeServices authorizationCodeServices,
            ClientDetailsService clientDetailsService,
            OAuth2RequestFactory requestFactory)
    {
        List<TokenGranter> tokenGranters = new ArrayList<>();
        //添加授权码模式
        tokenGranters.add(
                new AuthorizationCodeTokenGranter(tokenServices,authorizationCodeServices,clientDetailsService,requestFactory)
        );
        //添加刷新令牌模式
        tokenGranters.add(
                new RefreshTokenGranter(tokenServices,clientDetailsService,requestFactory)
        );
        //添加隐式授权模式
        tokenGranters.add(
                new ImplicitTokenGranter(tokenServices,clientDetailsService,requestFactory)
        );
        //添加客户端模式
        tokenGranters.add(
                new ClientCredentialsTokenGranter(tokenServices,clientDetailsService,requestFactory)
        );
        if (Objects.nonNull(authenticationManager)) {
            //添加密码模式
            tokenGranters.add(
                    new ResourceOwnerPasswordTokenGranter(authenticationManager,tokenServices,clientDetailsService,requestFactory)
            );
        }
        return tokenGranters;
    }

    /**
     * 使用非对称加密算法对token签名
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyPair());
        return converter;
    }
    /**
     * 密钥库中获取密钥对（公钥+私钥）
     */
    @Bean
    public KeyPair keyPair() {
        KeyStoreKeyFactory factory = new KeyStoreKeyFactory(
                new ClassPathResource("jwt.jks"),
                jksConfig.getKeypass().toCharArray()
        );
        KeyPair keyPair = factory.getKeyPair(jksConfig.getAilas(),jksConfig.getKeypass().toCharArray());
        return keyPair;
    }

    /**
     * JWT token存储模式
     */
    @Bean
    public JwtTokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

}
