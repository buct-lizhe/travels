package com.baizhi.travels.service;

import com.baizhi.travels.entity.User;

public interface UserService {
    //注册
    void register(User user );

    //登录
    User selectUserByUser(User user);
}
