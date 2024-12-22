package top.torx;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import top.torx.biz.demo.BizDemoApplication;


/**
 * @author LIUYUHUA
 * @version 1.0
 * @date 2024/1/24 14:10
 */
@SpringBootTest(classes = BizDemoApplication.class)
public class ApplicationTest {

    @Autowired
    private Environment environment;
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void test() {
        System.out.println(environment.getProperty("spring.data.redis.database"));
        for (String beanDefinitionName : applicationContext.getBeanDefinitionNames()) {
            System.out.println(applicationContext.getBean(beanDefinitionName));
        }
    }
}
