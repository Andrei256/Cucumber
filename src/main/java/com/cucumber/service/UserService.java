package com.cucumber.service;

import com.cucumber.model.Role;
import com.cucumber.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    void save(User user);

    List<User> getAll();

    User get(long id);

    void delete(long id);

    boolean addUser(User user);

    List<User> getAllWhereNotEqual(User user);

    void editUserActive(long id, boolean active);

    List<User> searchWithoutAnAuthorizedUser(User user, String keyword);

    void editUserRole(long id, Set<Role> roles);

    void editDataForShop(long id, User user);

    void editUser(long id, User user);

    boolean activateUser(String code);
}
