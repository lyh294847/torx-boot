package top.torx.lock.test;

import cn.hutool.core.thread.ThreadUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.task.TaskExecutor;
import top.torx.biz.demo.BizDemoApplication;
import top.torx.core.component.cache.CacheOps;
import top.torx.core.component.lock.LockOps;
import top.torx.dto.Person;

import java.util.concurrent.TimeUnit;


/**
 * @author LIUYUHUA
 * @version 1.0
 * @date 2024/1/24 14:10
 */
@SpringBootTest(classes = BizDemoApplication.class)
public class LockTest {

    @Autowired
    private LockOps lockOps;
    @Autowired
    private TaskExecutor taskExecutor;

    @Test
    public void test() throws InterruptedException {

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            taskExecutor.execute(()->{
                System.out.println(lockOps.tryLock("a", 1, 5, TimeUnit.SECONDS)+" "+ Thread.currentThread().getName());
                ThreadUtil.sleep(1000);
            });
        }
        ThreadUtil.sleep(5000);
        System.out.println(lockOps.tryLock("a", 1, 5, TimeUnit.SECONDS)+" "+ Thread.currentThread().getName());

    }
}
