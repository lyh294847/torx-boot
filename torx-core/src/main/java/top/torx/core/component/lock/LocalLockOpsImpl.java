package top.torx.core.component.lock;

import cn.hutool.core.thread.ThreadUtil;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.ConcurrentHashMap;
import java.lang.ref.WeakReference;

/**
 * 本地锁实现
 *
 * @Author: LiuYuHua
 * @Date: 2024/12/22 18:30
 */
public class LocalLockOpsImpl implements LockOps {

    private static class LockInfo {
        private final Thread ownerThread;
        private final long expireTime;
        private int reentrantCount;

        public LockInfo(Thread ownerThread, long expireTime) {
            this.ownerThread = ownerThread;
            this.expireTime = expireTime;
            this.reentrantCount = 1;
        }

        public boolean isExpired() {
            return System.currentTimeMillis() > expireTime;
        }

        public void increment() {
            reentrantCount++;
        }

        public void decrement() {
            reentrantCount--;
        }

        public int getCount() {
            return reentrantCount;
        }
    }

    // 使用WeakHashMap作为ConcurrentHashMap的值
    private final ConcurrentHashMap<String, WeakReference<LockInfo>> lockMap = new ConcurrentHashMap<>();

    @Override
    public boolean tryLock(String key, long waitTime, long leaseTime, TimeUnit unit) {
        long startTime = System.currentTimeMillis();
        long waitMillis = unit.toMillis(waitTime);
        long expireTime = System.currentTimeMillis() + unit.toMillis(leaseTime);

        while (System.currentTimeMillis() - startTime < waitMillis) {
            // 检查现有锁
            WeakReference<LockInfo> currentLockRef = lockMap.get(key);
            LockInfo currentLock = currentLockRef != null ? currentLockRef.get() : null;

            // 检查是否是当前线程的重入
            if (currentLock != null && currentLock.ownerThread == Thread.currentThread()) {
                if (!currentLock.isExpired()) {
                    currentLock.increment();
                    return true;
                } else {
                    lockMap.remove(key, currentLockRef);
                }
            }

            // 如果锁不存在或已过期或已被GC
            if (currentLock == null || currentLock.isExpired()) {
                lockMap.remove(key, currentLockRef);

                // 创建新锁
                LockInfo newLock = new LockInfo(Thread.currentThread(), expireTime);
                WeakReference<LockInfo> newLockRef = new WeakReference<>(newLock);

                // 尝试设置新锁
                if (lockMap.putIfAbsent(key, newLockRef) == null) {
                    return true;
                }
            }

            // 短暂休眠避免CPU空转
            ThreadUtil.sleep(10);
        }
        return false;
    }

    @Override
    public void releaseLock(String key) {
        WeakReference<LockInfo> lockRef = lockMap.get(key);
        if (lockRef != null) {
            LockInfo lockInfo = lockRef.get();
            if (lockInfo != null && lockInfo.ownerThread == Thread.currentThread()) {
                lockInfo.decrement();
                if (lockInfo.getCount() <= 0) {
                    lockMap.remove(key, lockRef);
                }
            }
        }
    }

}
