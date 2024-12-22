package top.torx.cache.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.torx.biz.demo.BizDemoApplication;
import top.torx.core.component.cache.CacheOps;
import top.torx.dto.Person;


/**
 * @author LIUYUHUA
 * @version 1.0
 * @date 2024/1/24 14:10
 */
@SpringBootTest(classes = BizDemoApplication.class)
public class CacheTest {

    @Autowired
    private CacheOps cacheOps;

    @Test
    public void test() throws InterruptedException {
        Person person = new Person();
        person.setName("张三");
        person.setAge(18);
        cacheOps.set("b", person);
//        cacheOps.expire("b", Duration.ofSeconds(1));
//        cacheOps.del("b");

        Person s = cacheOps.get("b");
        System.out.println(s);
        Thread.sleep(550);

        s = cacheOps.get("b");
        System.out.println(s);
        Thread.sleep(550);

        s = cacheOps.get("b");
        System.out.println(s);
        Thread.sleep(510);


        s = cacheOps.get("b");
        System.out.println(s);
        Thread.sleep(510);

    }
}
