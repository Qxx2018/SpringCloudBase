package com.itheima.common.config.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redisTemplate自定义序列化配置，更改其默认序列化器
 * @author XinXingQian
 */
@Configuration
public class RedisConfigure extends CachingConfigurerSupport {


    @Bean(name = "redisTemplateTemp")
    public RedisTemplate<String, Object> redisTemplateTemp(RedisConnectionFactory redisConnectionFactory) {

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

        //================================= RedisTemplate 序列化设置 start ==============================


        //使用StringRedisSerializer来序列化和反序列化Redis的key值
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // 使用GenericJackson2JsonRedisSerializer来序列化和反序列化Redis的value值
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer(objectMapper);

        //使用 <String, Object> 泛型，避免类型转换
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        //配置连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        //key、hashKey采用String的序列化方式
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);

        //value、HashValue采用Jackson的序列化方式
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);

        //初始化函数，使用以上设置的序列化参数
        redisTemplate.afterPropertiesSet();
        //================================= RedisTemplate 序列化设置 end ================================
        return redisTemplate;
    }

}
