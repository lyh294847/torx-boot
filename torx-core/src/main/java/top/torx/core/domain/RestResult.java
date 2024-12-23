package top.torx.core.domain;

import top.torx.core.exception.ExceptionCode;

import java.util.HashMap;

/**
 * 请求响应
 *
 * @Author: LiuYuHua
 * @Date: 2024/12/22 11:54
 */
public class RestResult extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    public static final String CODE_TAG = "code";

    /**
     * 返回内容
     */
    public static final String MSG_TAG = "msg";

    /**
     * 数据对象
     */
    public static final String DATA_TAG = "data";


    /**
     * 初始化一个新创建的 AjaxResult 对象，使其表示一个空消息。
     */
    public RestResult() {
    }


    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code 返回编码
     * @param msg  返回内容
     * @param data 数据对象
     */
    public RestResult(int code, String msg, Object data) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        super.put(DATA_TAG, data);
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static RestResult success() {
        return RestResult.success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static RestResult success(Object data) {
        return RestResult.success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static RestResult success(String msg) {
        return RestResult.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static RestResult success(String msg, Object data) {
        return new RestResult(ExceptionCode.SUCCESS.getCode(), msg, data);
    }

    /**
     * 方便链式调用
     *
     * @param key   键
     * @param value 值
     * @return 数据对象
     */
    @Override
    public RestResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}