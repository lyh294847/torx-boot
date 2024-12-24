package top.torx.app.user.service.impl;

import org.springframework.stereotype.Service;
import top.torx.app.user.entity.User;
import top.torx.app.user.mapper.UserMapper;
import top.torx.app.user.service.UserService;
import top.torx.mybatis.extension.SuperServiceImpl;

/**
 * @author LiuYuHua
 * @date 2024/12/24 11:20
 */
@Service
public class UserServiceImpl extends SuperServiceImpl<UserMapper, User> implements UserService {
}
