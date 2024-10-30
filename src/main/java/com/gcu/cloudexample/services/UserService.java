package com.gcu.cloudexample.services;

import com.gcu.cloudexample.dao.UserDAO;
import com.gcu.cloudexample.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public User registerUser(User user) {
        if (userDAO.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        // Password is stored as plain text
        userDAO.save(user);
        return user;
    }

    public Optional<User> findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    public User authenticate(String username, String password) {
        Optional<User> userOpt = userDAO.findByUsername(username);
        if (userOpt.isPresent() && password.equals(userOpt.get().getPassword())) {
            return userOpt.get();
        }
        return null;
    }

    // Add other methods as needed
}