package com.itheima.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author 10445
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.itheima.demo.rabbit2"})
@EnableConfigurationProperties
public class DemoRabbit2Application {
    public static void main(String[] args) {
        SpringApplication.run(DemoRabbit2Application.class,args);
        System.out.println("=======================DemoRabbit2Application Start ==================================");
    }
}
