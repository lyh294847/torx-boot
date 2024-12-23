package top.torx.redisson;

import org.redisson.api.RedissonClient;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import top.torx.core.component.cache.CacheOps;
import top.torx.core.component.cache.CacheProperties;
import top.torx.core.component.lock.LockOps;
import top.torx.redisson.cache.CustomRedissonSpringCacheManager;
import top.torx.redisson.cache.RedissonCacheOpsImpl;
import top.torx.redisson.lock.RedissonLockOpsImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * redisson自动配置
 *
 * @Author: LiuYuHua
 * @Date: 2024/12/22 11:54
 */
public class BootRedissonAutoConfiguration {

    @Bean
    public CacheOps redissonCacheOpsImpl(RedissonClient redissonClient) {
        return new RedissonCacheOpsImpl(redissonClient);
    }


    @Bean
    public LockOps redissonLockOpsImpl(RedissonClient redissonClient) {
        return new RedissonLockOpsImpl(redissonClient);
    }


    @Bean
    @Primary
    public CacheManager redissonSpringCacheManager(RedissonClient redissonClient, CacheProperties cacheProperties) {
        RedissonSpringCacheManager cacheManager = new CustomRedissonSpringCacheManager(redissonClient, cacheProperties);
        cacheManager.setAllowNullValues(cacheProperties.isCacheNull());

        // 创建缓存配置Map
        Map<String, CacheConfig> configMap = new HashMap<>();

        // 设置特定缓存配置
        if (cacheProperties.getConfigs() != null) {
            cacheProperties.getConfigs().forEach((name, config) -> {
                configMap.put(name, createCacheConfig(config));
            });
        }

        // 设置配置到cacheManager
        cacheManager.setConfig(configMap);


        return cacheManager;
    }

    private CacheConfig createCacheConfig(CacheProperties.Cache cacheConfig) {
        CacheConfig redissonConfig = new CacheConfig();

        // 设置过期时间
        redissonConfig.setTTL(cacheConfig.getExpire().toMillis());

        // 设置最大容量
        redissonConfig.setMaxSize(cacheConfig.getMaxSize());

        return redissonConfig;
    }
}
