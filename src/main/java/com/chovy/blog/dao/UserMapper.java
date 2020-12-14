package com.chovy.blog.dao;

import com.chovy.blog.entity.User;
import org.springframework.stereotype.Repository;


@Repository
public interface UserMapper {
    User selectById(int id);

    User selectByName(String username);

    User selectByEmail(String email);

    int insertUser(User user);

    int updateStatus(int id, int status);

    int updateHeader(int id, String headerUrl);

    int updatePassword(int id, String password);
}
