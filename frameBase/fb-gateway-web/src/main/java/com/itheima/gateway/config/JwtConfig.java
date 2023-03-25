package com.itheima.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT配置
 * @author XinXingQian
 */
@Data
@Component
@ConfigurationProperties("sc.jwt.config")
public class JwtConfig {
    /**
     * 密钥
     */
    private String tokenSecret;
    /**
     * 过期时间（天）
     */
    private Integer durationDayTime;
}
