package top.torx.core.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import top.torx.core.context.ContextUtil;

import java.lang.reflect.Method;

/**
 * @Author: LiuYuHua
 * @Date: 2024/12/22 11:54
 */
@Slf4j
public class SpelHelper {

    private static final String HASH = "#";

    /**
     * 用于SpEL表达式解析.
     */
    private static final SpelExpressionParser spelExpressionParser = new SpelExpressionParser();

    /**
     * 用于获取方法参数定义名字.
     */
    private static final DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();

    public static String getValBySpEL(String spEL, Method method, Object[] args) {
        // 获取方法形参名数组
        String[] paramNames = nameDiscoverer.getParameterNames(method);
        return getValBySpEL(spEL, paramNames, args);
    }

    public static String getValBySpEL(String spEl, String[] paramNames, Object[] args) {
        // 获取方法形参名数组
        if (spEl.contains(HASH) && paramNames != null && paramNames.length > 0) {
            Expression expression = spelExpressionParser.parseExpression(spEl);
            // spring的表达式上下文对象
            EvaluationContext context = new StandardEvaluationContext();
            // 给上下文赋值
            for (int i = 0; i < args.length; i++) {
                context.setVariable(paramNames[i], args[i]);
                context.setVariable("p" + i, args[i]);
            }
            context.setVariable("context", ContextUtil.getLocalMap());
            Object value = expression.getValue(context);
            return value.toString();
        }
        return spEl;
    }
}
