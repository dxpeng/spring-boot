package com.xpit.springbootjpa.dao;

import com.xpit.springbootjpa.entity.Users;

import org.springframework.data.jpa.repository.JpaRepository;


/**
 * 持久层
 */

public interface UsersDao extends JpaRepository<Users, Integer> {
}
