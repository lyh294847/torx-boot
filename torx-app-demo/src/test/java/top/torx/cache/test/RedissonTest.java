package top.torx.cache.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.torx.biz.demo.BizDemoApplication;
import top.torx.core.component.cache.CacheOps;

/**
 * @Author: LiuYuHua
 * @Date: 2024/12/22 14:37
 */
@SpringBootTest(classes = BizDemoApplication.class)
public class RedissonTest {

    @Autowired
    private CacheOps cacheOps;

    @Test
    public void test(){

        Object a = cacheOps.get("a", (key) -> {
            System.out.println("111");
            return null;
        }, true);
        System.out.println(a);

        a = cacheOps.get("a", (key) -> {
            System.out.println("111");
            return null;
        }, false);

        System.out.println(a);
    }
}
