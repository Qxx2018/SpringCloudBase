package com.itheima.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 10445
 */
@SpringBootApplication(scanBasePackages = {"com.itheima"})
@EnableFeignClients
@EnableDiscoveryClient
@EnableConfigurationProperties
public class GatewayWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayWebApplication.class,args);
        System.out.println("=======================GatewayWebApplication Srart ==================================");
    }
}
