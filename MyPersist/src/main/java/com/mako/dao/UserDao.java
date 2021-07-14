package com.mako.dao;

import com.mako.pojo.User;

import java.util.List;

public interface UserDao {
    List<User> findAll(User user);
    User findOne(User user);
    int updateById(User user);
    int deleteById(User user);
    int insertOne(User user);
}
