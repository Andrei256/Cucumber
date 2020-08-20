package com.cucumber.repository;

import com.cucumber.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    @Query(value = "SELECT u FROM User u WHERE u.username LIKE '%' || :keyword || '%'")
    List<User> search(@Param("keyword") String keyword);
}
