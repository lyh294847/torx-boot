package top.torx.caffeine;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import top.torx.caffeine.cache.CaffeineCacheOpsImpl;
import top.torx.caffeine.cache.CustomCaffeineCacheManager;
import top.torx.core.component.cache.CacheOps;
import top.torx.core.component.cache.CacheProperties;

import java.util.Map;
import java.util.Optional;

/**
 * Caffeine自动配置
 *
 * @Author: LiuYuHua
 * @Date: 2024/12/22 11:54
 */
@RequiredArgsConstructor
public class BootCaffeineAutoConfiguration {


    @Bean
    public CacheOps caffeineCacheOps(CacheProperties cacheProperties) {
        return new CaffeineCacheOpsImpl(cacheProperties);
    }


    @Bean
    @Primary
    public CacheManager caffeineCacheManager(CacheProperties cacheProperties) {
        CaffeineCacheManager cacheManager = new CustomCaffeineCacheManager();
        cacheManager.setAllowNullValues(cacheProperties.isCacheNull());
        // 配置了这里，就必须事先在配置文件中指定key 缓存才生效
        Map<String, CacheProperties.Cache> configs = cacheProperties.getConfigs();
        Optional.ofNullable(configs).ifPresent((config) -> config.forEach((key, cache) -> {
            cacheManager.registerCustomCache(key, getCaffeine(cache).build());
        }));

        cacheManager.setCaffeine(getCaffeine(cacheProperties.getDef()));
        return cacheManager;
    }

    private Caffeine<Object, Object> getCaffeine(CacheProperties.Cache cacheConfig) {
        return Caffeine.newBuilder()
                .initialCapacity(500)
                .maximumSize(cacheConfig.getMaxSize())
                .expireAfterWrite(cacheConfig.getExpire());
    }

}
