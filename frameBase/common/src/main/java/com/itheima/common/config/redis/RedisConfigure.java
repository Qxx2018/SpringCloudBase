package com.itheima.common.config.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.itheima.common.config.redis.manager.RedisCacheManagerExtend;
import com.itheima.sys.core.constants.NumConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;

/**
 * redis缓存配置
 * @author XinXingQian
 */
@Configuration
@EnableCaching
public class RedisConfigure extends CachingConfigurerSupport {

    /**
     * 缓存前缀
     */
    @Value("${spring.application.name}")
    private String cacheKeyPrefix;

    /**
     * 自定义序列化配置，更改其默认序列化器
     * @param redisConnectionFactory
     * @return
     */
    @SuppressWarnings("rawtypes")
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        //配置对象映射器
        ObjectMapper objectMapper = new ObjectMapper();
        //指定要序列化的域，file、get、set，以及修饰符范围。
        //ANY指包括private和public修饰符范围
        objectMapper.setVisibility(
                PropertyAccessor.ALL,
                JsonAutoDetect.Visibility.ANY
        );
        //指定序列化的输入类型，类的信息也将添加到json中，这样才可以根据类名反序列化
        objectMapper.activateDefaultTyping(
                LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.WRAPPER_ARRAY
        );
        //jackson序列化默认存在不认识字段报错，用户可以自己设置将其忽略
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //使用StringRedisSerializer来序列化和反序列化Redis的key值
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // 使用GenericJackson2JsonRedisSerializer来序列化和反序列化Redis的value值
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer(objectMapper);
        //================================= RedisTemplate 序列化设置 start ==============================

        RedisTemplate redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);
        //初始化函数，使用以上设置的序列化参数
        redisTemplate.afterPropertiesSet();
        //================================= RedisTemplate 序列化设置 end ================================
        return redisTemplate;
    }

    /**
     * 缓存管理器配置
     * @return
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();

        redisCacheConfiguration = redisCacheConfiguration
                //设置缓存管理器管理的缓存的默认过期时间
                .entryTtl(Duration.ofDays(NumConstant.NUM_1))
                //设置 key为string序列化
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                //设置value为json序列化
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .computePrefixWith(cacheName -> cacheKeyPrefix + "::" + cacheName + "::")

                //不缓存空值
                .disableCachingNullValues();

        return RedisCacheManagerExtend.builder(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                //缓存空间集合
                .initialCacheNames(Collections.emptySet())
                //对每个缓存空间应用不同的配置
                .withInitialCacheConfigurations(new HashMap<>(NumConstant.NUM_3))
                .build();
    }
}
