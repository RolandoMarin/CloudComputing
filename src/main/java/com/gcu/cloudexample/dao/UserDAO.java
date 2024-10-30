package com.gcu.cloudexample.dao;

import com.gcu.cloudexample.model.User;

import java.util.Optional;

public interface UserDAO {
    Optional<User> findByUsername(String username);
    void save(User user);
    boolean existsByUsername(String username);
}