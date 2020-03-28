package com.xpit.springbootredis.controller;

import com.xpit.springbootredis.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 控制层
 */
@RestController
public class UserController {
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @GetMapping("/user")
    public void getUser() {
        ValueOperations<String, String> ops1 = stringRedisTemplate.opsForValue();
        // 存字符串
        ops1.set("kname", "张三丰");
        System.out.println(ops1.get("kname"));
        // 存对象
        User user = new User();
        user.setId(1);
        user.setName("洪七公");
        user.setAddress("射雕英雄传");
        ValueOperations ops2 = redisTemplate.opsForValue();
        ops2.set("user1", user);
        System.out.println(ops2.get("user1"));
    }
}
