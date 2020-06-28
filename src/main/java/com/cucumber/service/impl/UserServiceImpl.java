package com.cucumber.service.impl;

import com.cucumber.model.User;
import com.cucumber.repository.UserRepository;
import com.cucumber.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User get(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
