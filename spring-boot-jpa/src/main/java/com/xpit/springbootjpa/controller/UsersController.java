package com.xpit.springbootjpa.controller;

import com.xpit.springbootjpa.entity.Users;
import com.xpit.springbootjpa.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 控制层
 */
@RestController
@RequestMapping("/v1/user")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @GetMapping("lists")
    public List<Users> getAllUsers() {
        return usersService.getAllUsers();
    }
}
