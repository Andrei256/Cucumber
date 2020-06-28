package com.cucumber.service;

import com.cucumber.model.User;

import java.util.List;

public interface UserService {
    void save(User user);
    List<User> getAll();
    User get(Long id);
    void delete(Long id);
}
