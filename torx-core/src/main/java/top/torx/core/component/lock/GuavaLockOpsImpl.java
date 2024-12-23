package top.torx.core.component.lock;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.google.common.util.concurrent.Striped;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * Guava本地锁实现
 *
 * @author LiuYuHua
 * @date 2023/12/20 20:53
 */
@Slf4j
public class GuavaLockOpsImpl implements LockOps {

    /**
     * 参数stripes的数量表示能同时有几个key
     */
    private final Striped<Lock> stripedLock = Striped.lock(10);

    @Override
    public boolean tryLock(String key, long waitTime, long leaseTime, TimeUnit unit) {
        Lock lock = stripedLock.get(key);
        try {
            return lock.tryLock(waitTime, unit);
        } catch (InterruptedException e) {
            throw ExceptionUtil.wrapRuntime(e);
        }
    }

    @Override
    public void releaseLock(String key) {
        Lock lock = stripedLock.get(key);
        lock.unlock();
    }

}
