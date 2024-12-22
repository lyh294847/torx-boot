package top.torx.biz.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;


/**
 * @author LIUYUHUA
 * @version 1.0
 * @date 2023/12/19 21:18
 */
@SpringBootApplication
@Slf4j
@EnableCaching
public class DemoApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(DemoApplication.class, args);
        Environment env = application.getEnvironment();
        log.info("\n----------------------------------------------------------\n\t" +
                        "应用 '{}' 运行成功! 访问连接:\n\t" +
                        "Swagger文档: \t\thttp://{}:{}/doc.html\n\t" +
                        "数据库监控: \t\thttp://{}:{}/druid\n" +
                        "----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                "127.0.0.1",
                env.getProperty("server.port"),
                "127.0.0.1",
                env.getProperty("server.port"));
    }
}