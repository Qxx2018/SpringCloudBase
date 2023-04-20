package com.itheima.common.config.redis.redisson.extension;

import lombok.Data;
import org.redisson.config.SslProvider;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;
import java.net.URL;

/**
 * @author XinXingQian
 */
@Data
public class BaseConfig<T extends org.redisson.config.BaseConfig<T>> implements Serializable {
    private static final long serialVersionUID = 3231650202968900706L;
    private int idleConnectionTimeout = 10000;
    /**
     * 连接超时，单位：毫秒
     */
    @Value("${sc.redisson.singleServerConfig.connectTimeout}")
    private int connectTimeout = 10000;
    /**
     * 命令等待超时，单位：毫秒
     */
    @Value("${sc.redisson.singleServerConfig.timeout}")
    private int timeout = 3000;
    /**
     * 命令失败重试次数
     */
    @Value("${sc.redisson.singleServerConfig.retryAttempts}")
    private int retryAttempts = 3;
    /**
     * 命令重试发送时间间隔，单位：毫秒
     */
    @Value("${sc.redisson.singleServerConfig.retryInterval}")
    private int retryInterval = 1500;
    /**
     * 密码
     */
    @Value("${sc.redisson.singleServerConfig.password}")
    private String password;

    private String username;
    /**
     * 单个连接最大订阅数量
     */
    @Value("${sc.redisson.singleServerConfig.subscriptionsPerConnection}")
    private int subscriptionsPerConnection = 5;
    /**
     * 客户端名称
     */
    @Value("${sc.redisson.singleServerConfig.clientName}")
    private String clientName;
    /**
     * 启用SSL终端识别
     */
    private boolean sslEnableEndpointIdentification = true;
    /**
     * SSL实现方式 （JDK或OPENSSL）来实现SSL连接。
     */
    private SslProvider sslProvider = SslProvider.JDK;
    /**
     * SSL信任证书库路径
     */
    private URL sslTruststore;
    /**
     * SSL信任证书库密码
     */
    private String sslTruststorePassword;
    /**
     * SSL钥匙库路径
     */
    private URL sslKeystore;
    /**
     * SSL钥匙库密码
     */
    private String sslKeystorePassword;
    private int pingConnectionInterval;
    private boolean keepAlive;
    private boolean tcpNoDelay;
}
