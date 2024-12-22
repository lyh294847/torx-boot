package top.torx.core.component.cache;

import java.time.Duration;
import java.util.function.Function;

/**
 * 缓存操作接口
 *
 * @Author: LiuYuHua
 * @Date: 2024/12/22 11:31
 */
public interface CacheOps {


    /**
     * 设置缓存
     */
    void set(String key, Object value);

    /**
     * 设置缓存
     */
    void setEx(String key, Object value, Duration expire);

    /**
     * 设置缓存过期时间
     */
    void expire(String key, Duration expire);

    /**
     * 获取缓存
     */
    <T> T get(String key);

    <T> T get(String key, Function<String, T> loader, boolean cacheNull);

    /**
     * 获取缓存，没有则加载可加载缓存
     *
     * @param key       key
     * @param loader    加载器
     * @param expire    加载的缓存过期时间
     * @param cacheNull 是否缓存空对象，true的话会避免loader加载
     */
    <T> T get(String key, Function<String, T> loader, Duration expire, boolean cacheNull);

    /**
     * 删除
     */
    long del(String... keys);
}
