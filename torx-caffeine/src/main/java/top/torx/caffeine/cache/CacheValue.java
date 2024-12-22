package top.torx.caffeine.cache;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Duration;

/**
 * 存储缓存值和过期时间
 *
 * @Author: LiuYuHua
 * @Date: 2024/12/22 14:14
 */

@Data
@AllArgsConstructor
public class CacheValue {

    private Object value;
    private Duration expire;  // 过期时间戳
}
