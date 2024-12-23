package top.torx.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author LiuYuHua
 * @date 2024/12/23 10:00
 */
@AllArgsConstructor
@Getter
public enum ExceptionCode implements BaseExceptionCode {

    SUCCESS(200, "成功"),
    PARAM_ERROR(400, "参数错误"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "请求方法不允许"),
    REQUEST_TIMEOUT(408, "请求超时"),
    CONFLICT(409, "资源冲突"),
    UNSUPPORTED_MEDIA_TYPE(415, "不支持的媒体类型"),
    TOO_MANY_REQUESTS(429, "请求过于频繁"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    NOT_IMPLEMENTED(501, "接口未实现"),
    BAD_GATEWAY(502, "网关错误"),
    SERVICE_UNAVAILABLE(503, "服务不可用"),
    GATEWAY_TIMEOUT(504, "网关超时"),
    HTTP_VERSION_NOT_SUPPORTED(505, "HTTP版本不支持");

    private final int code;
    private final String msg;

}
