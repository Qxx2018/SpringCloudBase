package com.itheima.oauth.certification.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Jwt 配置
 * @author 10445
 */
@Data
@Component
@ConfigurationProperties("sc.jwt.config")
public class JwtConfig {
    /**
     * 头类型：example: JWT
     */
    private String headerType;
    /**
     * 加密方式: example: HS256
     */
    private String headerAlg;
    /**
     * 加密密钥
     */
    private String tokenSecret;
    /**
     * 过期时间: day天
     */
    private Integer durationDayTime;


}