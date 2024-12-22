package top.torx.core.component.lock;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import top.torx.core.constant.BootConstant;


/**
 * 锁配置
 *
 * @Author: LiuYuHua
 * @Date: 2024/12/22 15:41
 */
@Data
@ConfigurationProperties(prefix = LockProperties.PREFIX)
public class LockProperties {
    public static final String PREFIX = BootConstant.PROJECT_PREFIX + ".lock";

    /**
     * 是否开启锁注解
     */
    private boolean enableAspect = true;


}