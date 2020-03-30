package com.xpit.springbootsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 控制层
 */
@RestController
public class UserController {
    @GetMapping("/user/list")
    public String getUsers() {
        return "user";
    }
}
