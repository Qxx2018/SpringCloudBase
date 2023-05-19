package com.itheima.oauth.certification.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * jks密钥文件配置
 * @author XinXingQian
 */
@ConfigurationProperties("sc.jks")
@Data
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
