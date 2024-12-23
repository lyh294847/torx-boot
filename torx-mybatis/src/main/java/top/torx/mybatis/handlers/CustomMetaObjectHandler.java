package top.torx.mybatis.handlers;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import top.torx.core.context.ContextUtil;
import top.torx.mybatis.extension.SuperEntity;

import java.time.LocalDateTime;

/**
 * @author LIUYUHUA
 * @version 1.0
 * @date 2023/12/20 19:46
 */
@Slf4j
public class CustomMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Object originalObject = metaObject.getOriginalObject();
        if (originalObject instanceof SuperEntity) {
            ((SuperEntity<?>) originalObject).setCreateBy(ContextUtil.getUserId()).setCreateAt(LocalDateTime.now());
        } else {
            this.strictInsertFill(metaObject, SuperEntity.CREATE_BY_FIELD, ContextUtil::getUserId, Long.class);
            this.strictInsertFill(metaObject, SuperEntity.CREATE_AT_FIELD, LocalDateTime::now, LocalDateTime.class);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "update_at", LocalDateTime::now, LocalDateTime.class);
        this.strictUpdateFill(metaObject, "update_by", LocalDateTime::now, LocalDateTime.class);
    }

}