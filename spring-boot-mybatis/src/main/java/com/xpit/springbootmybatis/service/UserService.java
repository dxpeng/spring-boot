package com.xpit.springbootmybatis.service;

import com.xpit.springbootmybatis.entity.User;

import java.util.List;

/**
 * 服务层接口
 * 定义业务接口
 */

public interface UserService {
    int addUser(User user);
    int deleteUserById(Integer id);
    int updateUserById(User user);
    User getUserById(Integer id);
    List<User> getAllUsers();
}
