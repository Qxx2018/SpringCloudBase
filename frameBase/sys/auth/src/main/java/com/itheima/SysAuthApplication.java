package com.itheima;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author 10445
 */
@SpringBootApplication(scanBasePackages = {"com.itheima"})
@EnableScheduling
@EnableFeignClients
@EnableDiscoveryClient
@EnableConfigurationProperties
public class SysAuthApplication {
    public static void main(String[] args) {
        System.setProperty("nacos.logging.default.config.enabled","false");
        SpringApplication.run(SysAuthApplication.class, args);
        System.out.println("=======================SysAuthApplication Start ==================================");
    }
}
