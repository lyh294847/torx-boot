package top.torx.core.component.cache;

import org.springframework.cache.support.NullValue;

import java.util.Map;

/**
 * 缓存操作抽象层
 *
 * @Author: LiuYuHua
 * @Date: 2024/12/22 15:10
 */
public abstract class AbstractCacheOps implements CacheOps {

    protected static final Object NULL_VALUE = NullValue.INSTANCE;

    /**
     * 判断缓存值是否为空对象
     *
     * @param value 返回值
     * @return 是否为空
     */
    protected static <T> boolean isNullValue(T value) {
        boolean isNull = value == null || NullValue.class.equals(value.getClass());
        return isNull || value.getClass().equals(Object.class) || (value instanceof Map && ((Map<?, ?>) value).isEmpty());
    }

    /**
     * 返回正常值 or null
     *
     * @param value 返回值
     * @return 对象
     */
    protected static <T> T returnValue(T value) {
        return isNullValue(value) ? null : value;
    }

}
