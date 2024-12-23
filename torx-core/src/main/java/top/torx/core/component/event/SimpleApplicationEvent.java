package top.torx.core.component.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author LiuYuHua
 * @date 2024/12/23 14:48
 */
public class SimpleApplicationEvent extends ApplicationEvent {

    public SimpleApplicationEvent(Object source) {
        super(source);
    }
}
