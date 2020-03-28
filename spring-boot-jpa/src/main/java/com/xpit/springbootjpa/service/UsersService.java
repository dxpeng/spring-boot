package com.xpit.springbootjpa.service;

import com.xpit.springbootjpa.dao.UsersDao;
import com.xpit.springbootjpa.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务层
 */
@Service
public class UsersService {
    @Autowired
    private UsersDao usersDao;

    public List<Users> getAllUsers(){
        return usersDao.findAll();
    }
}
