package top.torx.core.context;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.ttl.TransmittableThreadLocal;
import top.torx.core.constant.ContextConstant;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author LIUYUHUA
 * @version 1.0
 * @date 2023/12/18 22:49
 */
public class ContextUtil {

    private static final ThreadLocal<Map<String, String>> THREAD_LOCAL = new TransmittableThreadLocal<>();

    public static Map<String, String> getLocalMap() {
        Map<String, String> map = THREAD_LOCAL.get();
        if (map == null) {
            map = new ConcurrentHashMap<>(10);
            THREAD_LOCAL.set(map);
        }
        return map;
    }

    public static void set(String key, Object value) {
        Map<String, String> map = getLocalMap();
        map.put(key, Convert.toStr(value, StrUtil.EMPTY));
    }

    public static <T> T get(String key, Class<T> type) {
        Map<String, String> map = getLocalMap();
        return Convert.convert(type, map.get(key));
    }

    public static void setToken(String token) {
        set(ContextConstant.TOKEN_KEY, token);
    }

    public static String getToken() {
        return get(ContextConstant.TOKEN_KEY, String.class);
    }

    public static void setUserId(Long userId) {
        set(ContextConstant.USER_ID_KEY, userId);
    }

    public static Long getUserId() {
        return get(ContextConstant.USER_ID_KEY, Long.class);
    }

    public static void setAccount(String account) {
        set(ContextConstant.ACCOUNT_KEY, account);
    }

    public static String getAccount() {
        return get(ContextConstant.ACCOUNT_KEY, String.class);
    }

    public static void setUsername(String username) {
        set(ContextConstant.USERNAME_KEY, username);
    }

    public static String getUserType() {
        return get(ContextConstant.USER_TYPE_KEY, String.class);
    }

    public static void setUserType(Integer userType) {
        set(ContextConstant.USER_TYPE_KEY, userType);
    }

    public static Integer getUsername() {
        return get(ContextConstant.USERNAME_KEY, Integer.class);
    }

    public static void setOrgId(Long orgId) {
        set(ContextConstant.ORG_KEY, orgId);
    }

    public static Long getOrgId() {
        return get(ContextConstant.ORG_KEY, Long.class);
    }

    public static void setAdmin(boolean admin) {
        set(ContextConstant.ADMIN_KEY, admin);
    }

    public static boolean isAdmin() {
        return BooleanUtil.isTrue(get(ContextConstant.ADMIN_KEY, Boolean.class));
    }

    public static void setEnableAuth(boolean flag) {
        set(ContextConstant.ENABLE_AUTH_KEY, flag);
    }

    public static Boolean getEnableAuth() {
        return ObjectUtil.defaultIfNull(get(ContextConstant.ENABLE_AUTH_KEY, Boolean.class), true);
    }

    public static void setRequestIp(String ip) {
        set(ContextConstant.REQUEST_IP_KEY, ip);
    }

    public static String getRequestIp() {
        return get(ContextConstant.REQUEST_IP_KEY, String.class);
    }

    public static void setPath(String path) {
        set(ContextConstant.PATH_KEY, path);
    }

    public static void getPath() {
        get(ContextConstant.PATH_KEY, String.class);
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }


}
