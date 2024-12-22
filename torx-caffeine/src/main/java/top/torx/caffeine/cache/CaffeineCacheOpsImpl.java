package top.torx.caffeine.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import top.torx.core.component.cache.AbstractCacheOps;
import top.torx.core.component.cache.CacheProperties;

import java.time.Duration;
import java.util.function.Function;

/**
 * caffeine缓存实现
 *
 * @Author: LiuYuHua
 * @Date: 2024/12/22 11:31
 */
public class CaffeineCacheOpsImpl extends AbstractCacheOps {

    private final Cache<String, CacheValue> cache;

    public CaffeineCacheOpsImpl(CacheProperties cacheProperties) {
        // 使用Caffeine的清除机制，
        this.cache = Caffeine.newBuilder()
                .maximumSize(cacheProperties.getDef().getMaxSize())
                .expireAfter(new Expiry<String, CacheValue>() {
                    // 创建时设置过期时间
                    @Override
                    public long expireAfterCreate(String key, CacheValue value, long currentTime) {
                        return value.getExpire() == null ? Long.MAX_VALUE : value.getExpire().toNanos();
                    }

                    // 修改时设置过期时间
                    @Override
                    public long expireAfterUpdate(String key, CacheValue value,
                                                  long currentTime, long currentDuration) {
                        return value.getExpire() == null ? Long.MAX_VALUE : value.getExpire().toNanos();
                    }

                    // 读取时设置过期时间
                    @Override
                    public long expireAfterRead(String key, CacheValue value,
                                                long currentTime, long currentDuration) {
                        // 不设置，返回当前值
                        return currentDuration;
                    }
                })
                .build();
    }

    @SuppressWarnings("unchecked")
    private <T> T getValue(CacheValue cacheValue) {
        if (cacheValue == null) {
            return null;
        }
        return (T) cacheValue.getValue();
    }

    @Override
    public void set(String key, Object value) {
        setEx(key, value, null);
    }

    @Override
    public void setEx(String key, Object value, Duration expire) {
        if (value == null) {
            return;
        }
        cache.put(key, new CacheValue(value, expire));
    }

    @Override
    public void expire(String key, Duration expire) {
        CacheValue oldValue = cache.getIfPresent(key);
        if (oldValue != null) {
            cache.put(key, new CacheValue(oldValue.getValue(), expire));
        }
    }

    @Override
    public <T> T get(String key) {
        return get(key, null, false);
    }

    @Override
    public <T> T get(String key, Function<String, T> loader, boolean cacheNull) {
        return get(key, loader, null, cacheNull);
    }

    @Override
    public <T> T get(String key, Function<String, T> loader, Duration expire, boolean cacheNull) {
        T value = getValue(cache.getIfPresent(key));
        if (value == null && loader != null) {
            value = loader.apply(key);
            this.setEx(key, value == null && cacheNull ? NULL_VALUE : value, expire);
        }
        return returnValue(value);
    }

    @Override
    public long del(String... keys) {
        for (String key : keys) {
            cache.invalidate(key);
        }
        return keys.length;
    }


}