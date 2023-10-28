package com.pdp.java.service.api;

import com.pdp.java.entity.User;

import java.util.List;

public interface UserService {
    boolean register(User user);
    User login(String email);
    User getUserByEmail(String email);
    List<User> getUsers();
}