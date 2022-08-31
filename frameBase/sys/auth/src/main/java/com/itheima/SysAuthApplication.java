package com.itheima;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 10445
 */
@SpringBootApplication(scanBasePackages = {"com.itheima"})
public class SysAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(SysAuthApplication.class, args);
        System.out.println("=======================SysAuthApplication Start ==================================");
    }
}
