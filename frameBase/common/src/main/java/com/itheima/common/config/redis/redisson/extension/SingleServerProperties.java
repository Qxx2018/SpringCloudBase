package com.itheima.common.config.redis.redisson.extension;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * @author XinXingQian
 */
@Data
@Component
public class SingleServerProperties implements Serializable {

    private static final long serialVersionUID = -7202193388347201313L;
    /**
     * 单例模式配置
     */
    @Resource
    private SingleServerConfig singleServerConfig;
    /**
     * 线程池数量 默认值: 当前处理核数量 * 2
     */
    @Value("${sc.redisson.threads}")
    private int threads;
    /**
     * Netty线程池数量 默认值: 当前处理核数量 * 2
     */
    @Value("${sc.redisson.nettyThreads}")
    private int nettyThreads;
    /**
     * 传输模式
     */
    @Value("${sc.redisson.transportMode}")
    private String transportMode;

    @Resource
    private Codec codec;
}
