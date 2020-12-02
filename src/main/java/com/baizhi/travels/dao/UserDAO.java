package com.baizhi.travels.dao;

import com.baizhi.travels.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDAO {

    //按用户名查找用户
    User findUserByUserName( String username );
    //注册用户
    void save( User user );
    //用户登录
    User selectUserByUser( User user );

}
