package top.torx.mybatis.util;

import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import top.torx.core.domain.PageParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author LIUYUHUA
 * @version 1.0
 * @date 2024/9/4 15:12
 */
public class PageHelper {

    public static <E> Page<E> buildPage(PageParam<E> pageParam) {
        return buildPage(pageParam, Optional.ofNullable(pageParam.getModel()).map(Object::getClass).orElse(null));
    }

    public static <E> Page<E> buildPage(PageParam<E> pageParam, Class<?> entityClazz) {
        return buildPage(pageParam, entityClazz, null);
    }

    /**
     * @param entityClazz         实体类
     * @param sortPropertyConvert 排序字段映射转换
     * @return 分页
     */
    public static <E> Page<E> buildPage(PageParam<E> pageParam, Class<?> entityClazz, Function<String, String> sortPropertyConvert) {
        Page<E> searchPage = PageDTO.of(pageParam.getCurrent(), pageParam.getSize(), pageParam.isSearchCount());
        String sort = pageParam.getSort();
        String order = pageParam.getOrder();
        // 没有排序参数
        if (StrUtil.isEmpty(sort) || entityClazz == null) {
            return searchPage;
        }

        Map<String, String> propertyColumnMapping = TableHelper.getPropertyColumnMapping(entityClazz);

        List<OrderItem> orders = new ArrayList<>();
        String[] sortArr = StrUtil.splitToArray(sort, StrPool.COMMA);
        String[] orderArr = StrUtil.splitToArray(order, StrPool.COMMA);

        int len = Math.min(sortArr.length, orderArr.length);
        for (int i = 0; i < len; i++) {
            String humpSort = sortArr[i];

            String column;
            if (sortPropertyConvert != null) {
                column = sortPropertyConvert.apply(humpSort);
            } else {
                column = propertyColumnMapping.get(humpSort);
            }
            if (StrUtil.isEmpty(column)) {
                continue;
            }
            orders.add(StrUtil.equalsAny(orderArr[i], "ascending", "ascend") ? OrderItem.asc(column) : OrderItem.desc(column));
        }

        searchPage.setOrders(orders);
        return searchPage;
    }

}
