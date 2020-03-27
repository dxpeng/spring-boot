package com.xpit.springbootjdbctemplate.controller;

import com.xpit.springbootjdbctemplate.entity.User;
import com.xpit.springbootjdbctemplate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User前端控制层
 */
@RestController
@RequestMapping("/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public int addUser(@RequestBody User user) {
        // @RequestHeader(value = "Authentication") String token 接受请求头数据
        // @RequestHeader(value = "request-id",defaultValue = "123456") String requestId 可设置默认值
        System.out.println(user);
        userService.addUser(user);
        //开发中根据业务自定义返回JSON数据格式
        return 1;
    }

    @DeleteMapping("/del/{id}")
    public int delUserById(@PathVariable Integer id) {
        userService.deleteUserById(id);
        return 1;
    }

    @PutMapping("/update")
    public int updateUserById(@RequestBody User user) {
        userService.updateUser(user);
        return 1;
    }

    @GetMapping("/list/{id}")
    public User getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @GetMapping("/lists")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
