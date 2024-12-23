package top.torx.core.exception;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

/**
 * @author LiuYuHua
 * @date 2024/12/23 9:56
 */
@Getter
public class BizException extends RuntimeException {

    private final int code;

    public BizException(int code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = code;
    }

    public static BizException wrap(int code, String msg) {
        return new BizException(code, msg, null);
    }

    public static BizException wrap(ExceptionCode exceptionCode) {
        return wrap(exceptionCode.getCode(), exceptionCode.getMsg());
    }

    public static BizException wrap(ExceptionCode exceptionCode, String format, Object... args) {
        return wrap(exceptionCode.getCode(), StrUtil.format(format, args));
    }
}
