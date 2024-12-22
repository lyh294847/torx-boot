package top.torx.caffeine.cache;

import com.github.benmanes.caffeine.cache.AsyncCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCache;

/**
 * 原来的CaffeineCacheManager在设置允许allowNullValues=false时，如果缓存方法返回值为null时会直接报错，重写后如果返回null则不缓存
 *
 * @Author: LiuYuHua
 * @Date: 2024/12/22 11:31
 */
public class CustomCaffeineCacheManager extends CaffeineCacheManager {
    @Override
    protected Cache adaptCaffeineCache(String name, com.github.benmanes.caffeine.cache.Cache<Object, Object> cache) {
        return new CustomCaffeineCache(name, cache, isAllowNullValues());
    }

    @Override
    protected Cache adaptCaffeineCache(String name, AsyncCache<Object, Object> cache) {
        return new CustomCaffeineCache(name, cache, isAllowNullValues());
    }


    static class CustomCaffeineCache extends CaffeineCache {

        public CustomCaffeineCache(String name, com.github.benmanes.caffeine.cache.Cache<Object, Object> cache,
                                   boolean allowNullValues) {

            super(name, cache, allowNullValues);
        }

        public CustomCaffeineCache(String name, AsyncCache<Object, Object> cache, boolean allowNullValues) {
            super(name, cache, allowNullValues);
        }

        @Override
        public void put(Object key, Object value) {
            if (!isAllowNullValues() && value == null) {
                return;
            }
            super.put(key, value);
        }
    }
}
