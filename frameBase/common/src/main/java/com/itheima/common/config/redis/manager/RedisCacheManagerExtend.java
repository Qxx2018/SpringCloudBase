package com.itheima.common.config.redis.manager;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.itheima.common.constants.NumConstant;
import com.itheima.common.enums.SpecialCharEnum;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;

import java.time.Duration;

/**
 * redis缓存管理扩展
 * @author XinXingQian
 */
public class RedisCacheManagerExtend extends RedisCacheManager {


    /**
     * Creates new {@link RedisCacheManager} using given {@link RedisCacheWriter} and default
     * {@link RedisCacheConfiguration}.
     *
     * @param cacheWriter               must not be {@literal null}.
     * @param defaultCacheConfiguration must not be {@literal null}. Maybe just use
     *                                  {@link RedisCacheConfiguration#defaultCacheConfig()}.
     */
    public RedisCacheManagerExtend(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
    }

    /**
     * 重写-对于不同场景，设置超时时间 单位秒
     * Configuration hook for creating {@link RedisCache} with given name and {@code cacheConfig}.
     *
     * @param name        must not be {@literal null}. 符合‘缓存空间名’#‘超时时间’的格式； 时间单位（秒）
     * @param cacheConfig can be {@literal null}.
     * @return never {@literal null}.
     */
    @Override
    protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {

        //对于不同场景，我们希望有不同的超时时间
        //对createRedisCache函数做改写
        if (StrUtil.isNotBlank(name) && name.contains(SpecialCharEnum.POUND_SIGN.getSpecialChar())) {
            String[] nameStr = StrUtil.splitToArray(name,SpecialCharEnum.POUND_SIGN.getSpecialChar());
            if (nameStr.length == NumConstant.NUM_2) {
                //符合‘缓存空间名’#‘超时时间’的格式
                String ttlStr = nameStr[1];
                if (StrUtil.isNumeric(ttlStr)) {
                    //设置ttl 单位秒
                    long ttl = RandomUtil.randomLong(0,30) + Long.parseLong(ttlStr);
                    cacheConfig = cacheConfig.entryTtl(Duration.ofSeconds(ttl));
                }

            }
        }
        return super.createRedisCache(name, cacheConfig);
    }
}
