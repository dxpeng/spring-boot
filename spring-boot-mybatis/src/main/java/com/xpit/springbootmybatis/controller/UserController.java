package com.xpit.springbootmybatis.controller;

import com.xpit.springbootmybatis.entity.User;
import com.xpit.springbootmybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 控制层
 * 客户端请求API
 */
@RestController
@RequestMapping("/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public int addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @DeleteMapping("/del/{id}")
    public int delUserById(@PathVariable Integer id) {
        return userService.deleteUserById(id);
    }

    @PutMapping("/update")
    public int updateUserById(@RequestBody User user) {
        return userService.updateUserById(user);
    }

    @GetMapping("/list/{id}")
    public User getUserById(@PathVariable Integer id){
        return userService.getUserById(id);
    }

    @GetMapping("/lists")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
}
