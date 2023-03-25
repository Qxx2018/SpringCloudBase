package com.itheima.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 访问白名单
 * @author XinXingQian
 */
@Data
@Configuration
@ConfigurationProperties("sc.white")
public class WhiteUrlConfig {

    private List<String> urls;

}
