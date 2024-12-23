package top.torx.mybatis.extension;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @author LiuYuHua
 * @date 2024/12/23 10:40
 */
public class SuperServiceImpl<M extends SuperMapper<T>, T> extends ServiceImpl<M, T> implements SuperService<T> {
}
