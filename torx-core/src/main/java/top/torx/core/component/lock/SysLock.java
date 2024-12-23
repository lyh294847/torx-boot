package top.torx.core.component.lock;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 锁注解
 *
 * @Author: LiuYuHua
 * @Date: 2024/12/22 18:30
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface SysLock {

    /**
     * 锁key。如果不填，会根据类名和方法名自动构建
     *
     * @return {String}
     */
    String value() default "";

    /**
     * 获取锁时等待时间 毫秒
     */
    long waitTime() default 100;

    /**
     * 默认超时时间 毫秒
     */
    long leaseTime() default 5000;

    /**
     * 时间单位
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;

    /**
     * 得不到令牌的提示语
     */
    String msg() default "请求频率过快";

}