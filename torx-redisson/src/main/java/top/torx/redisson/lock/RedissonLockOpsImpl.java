package top.torx.redisson.lock;

import cn.hutool.core.exceptions.ExceptionUtil;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import top.torx.core.component.lock.LockOps;

import java.util.concurrent.TimeUnit;

/**
 * redisson锁实现
 *
 * @Author: LiuYuHua
 * @Date: 2024/12/22 17:55
 */
@RequiredArgsConstructor
public class RedissonLockOpsImpl implements LockOps {

    private final RedissonClient redissonClient;


    @Override
    public boolean tryLock(String key, long waitTime, long leaseTime, TimeUnit unit) {
        RLock lock = redissonClient.getLock(key);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            throw ExceptionUtil.wrapRuntime(e);
        }
    }

    @Override
    public void releaseLock(String key) {
        RLock lock = redissonClient.getLock(key);
        lock.unlock();
    }
}
