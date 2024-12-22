package top.torx.core.component.lock;

import java.lang.annotation.*;

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
public @interface Lock {

    /**
     * 锁key
     *
     * @return {String}
     */
    String value() default "";

    /**
     * 默认超时时间 毫秒
     */
    long timeout() default 5000;

    /**
     * 获取锁时等待时间 毫秒
     */
    long waitTimes() default 5000;

    /**
     * 得不到令牌的提示语
     */
    String msg() default "请求频率过快";

}