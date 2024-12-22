package top.torx.redisson.cache;

import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import top.torx.core.component.cache.CacheProperties;

/**
 * 原来的RedissonSpringCacheManager默认配置不能自定义缓存配置，这里重写一下
 *
 * @Author: LiuYuHua
 * @Date: 2024/12/22 17:20
 */
public class CustomRedissonSpringCacheManager extends RedissonSpringCacheManager {

    private final CacheProperties cacheProperties;

    public CustomRedissonSpringCacheManager(RedissonClient redisson, CacheProperties cacheProperties) {
        super(redisson);
        this.cacheProperties = cacheProperties;
    }

//    /**
//     * 这个配置可以加统一前缀，但是不确定会不会导致问题
//     * @param name
//     * @param config
//     * @return
//     */
//    @Override
//    protected RMapCache<Object, Object> getMapCache(String name, CacheConfig config) {
//        return super.getMapCache("a:"+name, config);
//    }

    @Override
    protected CacheConfig createDefaultConfig() {
        CacheConfig cacheConfig = new CacheConfig();
        cacheConfig.setTTL(cacheProperties.getDef().getExpire().toMillis());
        cacheConfig.setMaxSize(cacheProperties.getDef().getMaxSize());
        return cacheConfig;
    }
}
