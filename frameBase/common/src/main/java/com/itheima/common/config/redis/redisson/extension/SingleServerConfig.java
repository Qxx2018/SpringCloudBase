package com.itheima.common.config.redis.redisson.extension;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 单例模式配置
 * @author XinXingQian
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Component
public class SingleServerConfig extends BaseConfig {

    private static final long serialVersionUID = 7339680016696807180L;
    /**
     * 节点地址  host:port
     */
    @Value("${sc.redisson.singleServerConfig.address}")
    private String address;

    /**
     * 发布和订阅连接的最小空闲连接数
     */
    @Value("${sc.redisson.singleServerConfig.subscriptionConnectionMinimumIdleSize}")
    private int subscriptionConnectionMinimumIdleSize = 1;
    /**
     * 发布和订阅连接池大小
     */
    @Value("${sc.redisson.singleServerConfig.subscriptionConnectionPoolSize}")
    private int subscriptionConnectionPoolSize = 50;

    /**
     * 发布和订阅连接的最小空闲连接数
     */
    @Value("${sc.redisson.singleServerConfig.connectionMinimumIdleSize}")
    private int connectionMinimumIdleSize = 24;

    /**
     * 发布和订阅连接池大小
     */
    @Value("${sc.redisson.singleServerConfig.connectionPoolSize}")
    private int connectionPoolSize = 64;

    /**
     * 数据库编号
     */
    @Value("${sc.redisson.singleServerConfig.database}")
    private int database = 0;

    /**
     * DNS监测时间间隔，单位：毫秒
     */
    @Value("${sc.redisson.singleServerConfig.dnsMonitoringInterval}")
    private long dnsMonitoringInterval = 5000;
}
