package com.xpit.springbootmybatis.mapper;

import com.xpit.springbootmybatis.entity.User;

import java.util.List;

/**
 * 数据库访问层
 * 数据增删改查
 * 可在每个类加注解 @Mapper
 * 在入口类统一扫描 @MapperScan("com.xpit.springbootmybatis.mapper")
 */
public interface UserMapper {
    int addUser(User user);
    int deleteUserById(Integer id);
    int updateUserById(User user);
    User getUserById(Integer id);
    List<User> getAllUsers();
}
