package com.itheima.demo.rabbit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author 10445
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.itheima.demo.rabbit"})
@EnableConfigurationProperties
public class DemoRabbitApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoRabbitApplication.class,args);
        System.out.println("=======================DemoRabbitApplication Start ==================================");
    }
}
