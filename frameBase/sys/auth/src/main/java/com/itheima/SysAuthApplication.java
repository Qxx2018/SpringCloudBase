package com.itheima;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author 10445
 */
@EnableConfigurationProperties
@SpringBootApplication(scanBasePackages = {"com.itheima"})
@EnableScheduling
public class SysAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(SysAuthApplication.class, args);
        System.out.println("=======================SysAuthApplication Start ==================================");
    }
}
