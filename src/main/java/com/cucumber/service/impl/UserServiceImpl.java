package com.cucumber.service.impl;

import com.cucumber.model.Role;
import com.cucumber.model.User;
import com.cucumber.repository.UserRepository;
import com.cucumber.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;

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
    public User get(long id) {
        return userRepository.getOne(id);
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean addUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return false;
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
        return true;
    }

    @Override
    public List<User> getAllWhereNotEqual(User user) {
        List<User> listUsers = userRepository.findAll();
        listUsers.remove(userRepository.getOne(user.getId()));
        return listUsers;
    }

    @Override
    public void editUserActive(long id, boolean active) {
        User user = userRepository.getOne(id);
        user.setActive(active);
        userRepository.save(user);
    }

    @Override
    public List<User> searchWithoutAnAuthorizedUser(User user, String keyword) {
        List<User> listUsers = userRepository.search(keyword);
        listUsers.remove(userRepository.getOne(user.getId()));
        return listUsers;
    }

    @Override
    public void editUserRole(long id, Set<Role> roles) {
        User user = userRepository.getOne(id);
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public void editDataForShop(long id, User user) {
        User userFromDB = userRepository.getOne(id);
        userFromDB.setUsername(user.getEmail());
        userFromDB.setEmail(user.getEmail());
        userFromDB.setPhoneNumber(user.getPhoneNumber());
        userFromDB.setText(user.getText());
        userFromDB.setRoles(Collections.singleton(Role.SHOP));
        userRepository.save(userFromDB);
    }

    @Override
    public void editUser(long id, User user) {
        User userFromDB = userRepository.getOne(id);
        userFromDB.setUsername(user.getEmail());
        userFromDB.setEmail(user.getEmail());
        userFromDB.setPhoneNumber(user.getPhoneNumber());
        if (user.getText() != null) {
            userFromDB.setText(user.getText());
        }
        userRepository.save(userFromDB);
    }
}
