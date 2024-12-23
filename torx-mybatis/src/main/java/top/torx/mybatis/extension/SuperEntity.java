package top.torx.mybatis.extension;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import top.torx.core.domain.ValidWay;

import java.time.LocalDateTime;

/**
 * @author LiuYuHua
 * @date 2024/12/23 14:02
 */
@Data
@SuppressWarnings("unchecked")
public class SuperEntity<T extends SuperEntity<?>> {

    public static final String CREATE_AT_FIELD = "create_at";
    public static final String CREATE_BY_FIELD = "create_by";

    @NotNull(groups = ValidWay.Update.class)
    @TableId(type = IdType.ASSIGN_ID) // 雪花id 时间戳 + 工作机器ID + 序列号
    private Long id;

    @TableField(CREATE_BY_FIELD)
    private Long createBy;

    @TableField(CREATE_AT_FIELD)
    private LocalDateTime createAt;

    public T setId(Long id) {
        this.id = id;
        return (T) this;
    }

    public T setCreateBy(Long createBy) {
        this.createBy = createBy;
        return (T) this;
    }

    public T setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
        return (T) this;
    }

}
