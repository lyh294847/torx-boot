package top.torx.mybatis.util;

import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import top.torx.mybatis.extension.SuperEntity;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LIUYUHUA
 * @version 1.0
 * @date 2023/12/19 23:15
 */
public class TableHelper {

    private final static Map<Class<?>, Map<String, String>> PROPERTY_COLUMN_MAPPING_CACHE = new HashMap<>();

    /**
     * 获取属性和数据库字段的映射
     *
     * @param entityClass 实体类
     * @return 映射结果
     */
    public static Map<String, String> getPropertyColumnMapping(Class<?> entityClass) {
        return PROPERTY_COLUMN_MAPPING_CACHE.computeIfAbsent(entityClass, e -> {
            HashMap<String, String> result = new HashMap<>();
            TableInfo tableInfo = TableInfoHelper.getTableInfo(entityClass);
            if (tableInfo == null) {
                return result;
            }
            result.put(tableInfo.getKeyProperty(), tableInfo.getKeyColumn());
            List<TableFieldInfo> fieldList = tableInfo.getFieldList();
            for (TableFieldInfo tableFieldInfo : fieldList) {
                result.put(tableFieldInfo.getProperty(), tableFieldInfo.getColumn());
            }
            return result;
        });
    }

    /**
     * 获取id
     *
     * @param model
     * @return
     */
    public static Object getId(Object model) {
        if (model instanceof SuperEntity) {
            return ((SuperEntity<?>) model).getId();
        } else {
            // 实体没有继承 Entity 和 SuperEntity
            TableInfo tableInfo = TableInfoHelper.getTableInfo(model.getClass());
            if (tableInfo == null) {
                return null;
            }
            // 主键类型
            Class<?> keyType = tableInfo.getKeyType();
            if (keyType == null) {
                return null;
            }
            // id 字段名
            String keyProperty = tableInfo.getKeyProperty();

            // 反射得到 主键的值
            Field idField = ReflectUtil.getField(model.getClass(), keyProperty);
            return ReflectUtil.getFieldValue(model, idField);
        }
    }


}
