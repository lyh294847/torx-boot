package top.torx.core.component.lock;

import java.util.concurrent.TimeUnit;

/**
 * 锁操作接口
 *
 * @Author: LiuYuHua
 * @Date: 2024/12/22 15:41
 */
public interface LockOps {

    /**
     * 获取锁等待时间。超过时间拿不到锁则失败
     * 单位：毫秒
     */
    long WAIT_MILLIS = 100;

    /**
     * 默认超时时间。超过时间后锁自动释放
     * 单位：毫秒
     */
    long LEASE_MILLIS = 5000;


    /**
     * 获取锁
     *
     * @param key key
     * @return 成功/失败
     */
    default boolean tryLock(String key) {
        return tryLock(key, WAIT_MILLIS, LEASE_MILLIS, TimeUnit.MILLISECONDS);
    }

    /**
     * 获取锁
     *
     * @param key       key
     * @param waitTime  获取时等待时间
     * @param leaseTime 锁持有超时时间 这个时间对本地锁无效，本地锁不会自动释放，如果redis锁也不想自己释放，可以设置为-1
     * @return 成功/失败
     */
    boolean tryLock(String key, long waitTime, long leaseTime, TimeUnit unit);

    /**
     * 释放锁
     *
     * @param key key值
     */
    void releaseLock(String key);

}