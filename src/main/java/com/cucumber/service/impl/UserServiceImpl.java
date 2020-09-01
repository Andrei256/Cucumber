package com.cucumber.service.impl;

import com.cucumber.model.Role;
import com.cucumber.model.User;
import com.cucumber.repository.UserRepository;
import com.cucumber.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private  MailSender mailSender;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, MailSender mailSender) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
    }

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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActivationCode(UUID.randomUUID().toString());
        userRepository.save(user);
        log.info("User was added: " + user);
        sendMessage(user);
        return true;
    }

    private void sendMessage(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Привет, %s! \n" +
                            "Добро пожаловат в Cucumber. Пожалуйста, пройдите по ссылке: http://localhost:8080/active/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );
            mailSender.send(user.getEmail(), "Activation code", message);
        }
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
        log.info("User status was edited: " + user);
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
        log.info("User roles was edited: " + user);
    }

    @Override
    public void editDataForShop(long id, User user) {
        User userFromDB = userRepository.getOne(id);
        userFromDB.setUsername(user.getUsername());
        userFromDB.setEmail(user.getEmail());
        userFromDB.setPhoneNumber(user.getPhoneNumber());
        userFromDB.setText(user.getText());
        userFromDB.setRoles(new HashSet<>(Collections.singleton(Role.SHOP)));
        userRepository.save(userFromDB);
        log.info("User was edited. Shop opened: " + user);
    }

    @Override
    public void editUser(long id, User user) {
        User userFromDB = userRepository.getOne(id);
        userFromDB.setUsername(user.getUsername());
        userFromDB.setEmail(user.getEmail());
        userFromDB.setPhoneNumber(user.getPhoneNumber());
        if (user.getText() != null) {
            userFromDB.setText(user.getText());
        }
        userRepository.save(userFromDB);
        log.info("User was edited: " + user);
    }

    @Override
    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);
        if (user == null) {
            return false;
        }
        user.setActivationCode(null);
        userRepository.save(user);
        return true;
    }
}
