/**
 * 关于spring-boot整合redisson的相关方案网上有很多，
 * 主要是通过redisson-spring-boot-starter实现自动装配，
 * 现有的项目采用spring-cloud，nacos作为服务的发现和注册中心，
 * redis的相关配置信息不在本地保存，集中放到配置中心，
 * 项目启动时从配置中心读取配置文件，构建对应的SingleServerProperties，
 * 截止本文的发布，redisson-spring-boot-starter仍然不能很好的支持从nacos读取属性完成配置
 * （原因在于：SingleServerProperties需要的配置信息无法通过单一的POJO描述，
 * 其中的部分属性依赖其它的bean，必须从最小的单元装配，最后整合装配成）
 */
package com.itheima.common.config.redis.redisson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.common.config.redis.redisson.extension.SingleServerProperties;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * Redisson配置
 * 构建对应的SingleServerProperties 集中配置
 * https://segmentfault.com/a/1190000039743096?sort=votes
 * https://www.bookstack.cn/read/redisson-wiki-zh/spilt.6.2.-%E9%85%8D%E7%BD%AE%E6%96%B9%E6%B3%95.md
 * https://blog.csdn.net/Kevinnsm/article/details/124215652
 * ComponentScan注解一般和@Configuration注解一起使用，主要的作用就是定义包扫描的规则，然后根据定义的规则找出哪些需类需要自动装配到spring的bean容器中，然后交由spring进行统一管理。
 * @author XinXingQian
 */
@Configuration
@ComponentScan
public class RedissonConfiguration {

    @Resource
    SingleServerProperties singleServerProperties;

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient(SingleServerProperties singleServerProperties) throws IOException {
        ObjectMapper mapper=new ObjectMapper();
        String jsonString = mapper.writeValueAsString(singleServerProperties);
        Config config = Config.fromYAML(jsonString);
        return Redisson.create(config);
    }
}
