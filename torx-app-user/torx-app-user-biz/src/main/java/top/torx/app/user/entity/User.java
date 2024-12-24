package top.torx.app.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import top.torx.mybatis.extension.SuperEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LIUYUHUA
 * @version 1.0
 * @date 2023/12/19 21:11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@TableName("a_user")
public class User extends SuperEntity<User> {

    @TableField(exist = false)
    private final Map<String, Object> echoMap = new HashMap<>();

    @TableField(updateStrategy = FieldStrategy.NEVER)
    @NotBlank(message = "账号不能为空")
    @Size(min = 4, max = 20, message = "账号长度在4-20之间")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "账号只能包含字母和数字")
    private String account;

    @NotBlank(message = "用户名不能为空")
    @Size(max = 50, message = "用户名长度不能超过50")
    private String username;

    @NotNull( message = "用户类型不能为空")
    private Integer userType;

    @JsonIgnore
    private String hashPassword;

    @JsonIgnore
    private String plainPassword;

    // 用户状态,0=正常,1=禁用
    private Integer status;

    // 密码过期时间
    @JsonIgnore
    private LocalDateTime passwordExpireTime;

    // 密码错误次数
    @JsonIgnore
    private Integer passwordErrorNum;

    // 最后密码错误时间
    @JsonIgnore
    private LocalDateTime lastPasswordErrorTime;

    // 最后登录时间
    private LocalDateTime lastLoginTime;

    // 组织ID
    private Long orgId;

    @TableLogic
    @JsonIgnore
    private Boolean deleted;

}
