package com.itheima.oauth.certification.config;

import com.itheima.common.utils.RedisUtil;
import com.itheima.oauth.certification.constants.AuthConstants;
import com.itheima.oauth.certification.constants.JwtConstants;
import com.itheima.oauth.certification.dto.UserDetailsDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 内容增强
 * @author XinXingQian
 */
@Configuration
public class TokenEnhancerConfig {
    @Resource
    private RedisUtil<UserDetailsDTO> redisUtil;
    /**
     * JWT 内容增强
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            final Map<String, Object> additionalInfo = new HashMap<>();
            UserDetailsDTO userDetails = (UserDetailsDTO) authentication.getUserAuthentication().getPrincipal();
            additionalInfo.put(JwtConstants.JWT_USER_LOGIN_TIME,String.valueOf(System.currentTimeMillis()));
            additionalInfo.put(JwtConstants.JWT_TENANT_ID_KEY,userDetails.getTenantId());
            additionalInfo.put(JwtConstants.JWT_ACCOUNT_ID_KEY,String.valueOf(userDetails.getAccountId()));
            //TODO 缓存操作
            //将用户的资源权限放入缓存
            redisUtil.hset(AuthConstants.AUTH_USER_KEY,String.valueOf(userDetails.getAccountId()),userDetails);
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            return accessToken;
        };
    }
}
