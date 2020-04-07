package com.xpitd.swagger2.controller;

import com.xpitd.swagger2.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "用户管理相关接口")
@RequestMapping("/user")
public class UserController {
    @PostMapping("/add")
    @ApiOperation("添加用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", defaultValue = "张三"),
            @ApiImplicitParam(name = "address", value = "用户地址", defaultValue = "深圳", required = true)
    })
    public User addUser(String username, @RequestParam(required = true) String address) {
        User user = new User();
        user.setUsername(username);
        user.setAddress(address);
        return user;
    }

    @GetMapping("/list/{id}")
    @ApiOperation("根据id查用户")
    @ApiImplicitParam(name = "id", value = "用户id", defaultValue = "11", required = true)
    public User getUserById(@PathVariable Integer id) {
        User user = new User();
        user.setId(id);
        return user;
    }

    @PutMapping("/update")
    @ApiOperation("根据id更新用户")
    public User updateUserById(@RequestBody User user) {
        return user;
    }
}
