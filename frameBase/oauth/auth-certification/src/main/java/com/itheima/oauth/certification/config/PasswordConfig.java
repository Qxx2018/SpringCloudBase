package com.itheima.oauth.certification.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 加密配置
 * @author XinXingQian
 */
@Configuration
public class PasswordConfig {
    /**
     * 加密
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        //BCryptPasswordEncoder是Spring Security中的一个加密方法。BCryptPasswordEncoder方法采用了SHA-256+随机盐+密钥对密码进行加密
        return new BCryptPasswordEncoder(
                BCryptPasswordEncoder.BCryptVersion.$2Y,
                16);
    }
}
