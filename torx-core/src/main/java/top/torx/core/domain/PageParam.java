package top.torx.core.domain;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 分页参数
 *
 * @Author: LiuYuHua
 * @Date: 2024/12/22 11:54
 */
@Data
@Slf4j
public class PageParam<T> {

    /**
     * 请求页码
     */
    private long current = 1;

    /**
     * 每页大小
     */
    private long size = 10;

    /**
     * 是否查询数量
     */
    private boolean searchCount = true;

    /**
     * 排序字段
     */
    private String sort = "id";

    /**
     * 排序规则
     */
    private String order = "descending";

    /**
     * 关键字查询参数
     */
    private String keyword;

    /**
     * 查询对象
     */
    private T model;


}
