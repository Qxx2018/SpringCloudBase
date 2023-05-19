package com.itheima.oauth.certification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 10445
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.itheima"})
@EnableConfigurationProperties
@ConfigurationPropertiesScan
public class OauthCertificationApplication {
    public static void main(String[] args) {
        SpringApplication.run(OauthCertificationApplication.class,args);
        System.out.println("=======================OauthCertificationApplication Start ==================================");
    }
}
