package top.torx.core;

import cn.hutool.extra.spring.SpringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import top.torx.core.component.cache.CacheProperties;
import top.torx.core.component.lock.GuavaLockOpsImpl;
import top.torx.core.component.lock.LockOps;
import top.torx.core.component.lock.LockProperties;

/**
 * 核心自动配置
 *
 * @Author: LiuYuHua
 * @Date: 2024/12/22 11:54
 */
@RequiredArgsConstructor
@EnableConfigurationProperties({CacheProperties.class, LockProperties.class})
@Import(SpringUtil.class)
public class BootCoreAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public LockOps guavaLockOpsImpl() {
        return new GuavaLockOpsImpl();
    }

}
