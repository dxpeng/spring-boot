package com.xpit.springbootjdbctemplate.service;

import com.xpit.springbootjdbctemplate.dao.UserDao;
import com.xpit.springbootjdbctemplate.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


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
    public int deleteUserById(Integer id) {
        return userDao.deleteUserById(id);
    }
    //改
    public int updateUser(User user) {
        return userDao.updateUserById(user);
    }
    //查单个
    public User getUserById(Integer id) {
        return userDao.getUserById(id);
    }
    //查所有
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }
}
