package com.xpit.springbootjdbctemplate.service;

import com.xpit.springbootjdbctemplate.dao.UserDao;
import com.xpit.springbootjdbctemplate.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 服务层
 */
@Service
public class UserService {
    @Autowired
    UserDao userDao;

    //增
    public int addUser(User user) {
        return userDao.addUser(user);
    }

    //删
    public int deleteUserById(Integer id){
        return userDao.deleteUserById(id);
    }
}
