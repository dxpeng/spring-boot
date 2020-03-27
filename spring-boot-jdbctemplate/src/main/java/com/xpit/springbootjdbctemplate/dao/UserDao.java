package com.xpit.springbootjdbctemplate.dao;

import com.xpit.springbootjdbctemplate.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 数据库操作层
 * 增删改查
 */
@Repository
public class UserDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    //增
    public int addUser(User user) {
        int result = jdbcTemplate.update("INSERT INTO user(name,address) VALUES (?,?)", user.getName(), user.getAddress());
        return result;
    }
    //删
    public int deleteUserById(Integer id) {
        int result = jdbcTemplate.update("DELETE FROM user WHERE id=?", id);
        return result;
    }
    //改
    public int updateUserById(User user) {
        int result = jdbcTemplate.update("UPDATE user SET name=?,address=? WHERE id=?", user.getName(), user.getAddress(), user.getId());
        return result;
    }
    // 查单个
    public User getUserById(Integer id) {
        User user = jdbcTemplate.queryForObject("SELECT * FROM user WHERE id=?", new BeanPropertyRowMapper<>(User.class), id);
        return user;
    }
    //查所有
    public List<User> getAllUsers() {
        List<User> users = jdbcTemplate.query("SELECT * FROM user", new BeanPropertyRowMapper<>(User.class));
        return users;
    }
}
