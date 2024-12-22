package top.torx.redisson.cache;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.spring.cache.NullValue;
import top.torx.core.component.cache.AbstractCacheOps;
import top.torx.core.component.cache.RedisCacheOps;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * redisson缓存操作
 *
 * @Author: LiuYuHua
 * @Date: 2024/12/22 14:20
 */
@RequiredArgsConstructor
public class RedissonCacheOpsImpl extends AbstractCacheOps implements RedisCacheOps {

    protected static final Map<String, Object> KEY_LOCKS = new ConcurrentHashMap<>();

    private final RedissonClient redissonClient;


    @Override
    public void set(String key, Object value) {
        setEx(key, value, null);
    }

    @Override
    public void setEx(String key, Object value, Duration expire) {
        RBucket<Object> bucket = redissonClient.getBucket(key);
        bucket.set(value);
        if (expire != null) {
            bucket.expire(expire);
        }
    }

    @Override
    public void expire(String key, Duration expire) {
        RBucket<Object> bucket = redissonClient.getBucket(key);
        if (bucket.isExists()) {
            bucket.expire(expire);
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
        RBucket<T> bucket = redissonClient.getBucket(key);
        T value = bucket.get();
        if (value == null && loader != null) {
            // 加锁解决缓存击穿
            synchronized (KEY_LOCKS.computeIfAbsent(key, v -> new Object())) {
                value = bucket.get();
                if (value != null) {
                    return returnValue(value);
                }
                try {
                    value = loader.apply(key);
                    this.setEx(key, value == null && cacheNull ? NullValue.INSTANCE : value, expire);
                } finally {
                    KEY_LOCKS.remove(key);
                }
            }
        }

        return returnValue(value);
    }

    @Override
    public long del(String... keys) {
        return redissonClient.getKeys().delete(keys);
    }


}
