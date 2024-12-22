package top.torx.core.component.cache;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import top.torx.core.constant.BootConstant;

import java.time.Duration;
import java.util.Map;

/**
 * 通过 @Cacheable 注解标注的方法的缓存策略
 *
 * @Author: LiuYuHua
 * @Date: 2024/12/22 15:41
 */
@Data
@ConfigurationProperties(prefix = CacheProperties.PREFIX)
public class CacheProperties {
    public static final String PREFIX = BootConstant.PROJECT_PREFIX + ".cache";

    /**
     * 是否允许缓存null值
     */
    private boolean cacheNull = true;

    /**
     * 默认配置
     */
    private Cache def = new Cache();

    /**
     * 针对某几个具体的cacheName特殊配置
     * <p>
     * key:cacheName的名称
     * value:缓存配置
     */

    private Map<String, Cache> configs;

    @Data
    public static class Cache {

        /**
         * key 的过期时间
         * 默认1天过期
         * eg:
         * timeToLive: 1d
         */
        private Duration expire = Duration.ofDays(1);


        /**
         * 最大缓存个数，Caffeine必须指定，redisson可以指定为0，表示不限制
         */
        private int maxSize = 1000;

    }

}