package com.baizhi.travels.service;

import com.baizhi.travels.dao.UserDAO;
import com.baizhi.travels.entity.User;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserDAO userDAO;

    @Override
    public void register(User user) {

        if( userDAO.findUserByUserName( user.getUsername() )== null ) {
            userDAO.save( user );
        }
        else{
            throw  new RuntimeException("用户名已存在！！！");
        }

    }

    @Override
    public User selectUserByUser(User user) {
        return userDAO.selectUserByUser( user );
    }


}
