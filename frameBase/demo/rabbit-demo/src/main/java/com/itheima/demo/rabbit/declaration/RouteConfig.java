package com.itheima.demo.rabbit.declaration;

import org.springframework.context.annotation.Configuration;

/**
 * 路由键
 */
@Configuration
public class RouteConfig {
    /**
     * 普通路由a
     */
    public static final String A_ROUTING_KEY = "a-routing-key";
    /**
     * 死信路由a
     */
    public static final String  A_DEAD_LETTER_ROUTING_KEY = "a-dead-letter-routing-key";

}
