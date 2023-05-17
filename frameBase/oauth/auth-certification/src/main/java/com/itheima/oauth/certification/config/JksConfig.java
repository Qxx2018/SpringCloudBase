package com.itheima.oauth.certification.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * jks密钥文件配置
 * @author XinXingQian
 */
@Data
@Component
@ConfigurationProperties("sc.jks")
public class JksConfig {
    /**
     * 密钥口令和密钥库口令一致
     */
    private String keypass;
    /**
     * 别名
     */
    private String ailas;
}
