package com.pranshu.smartcontactmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pranshu.smartcontactmanager.entity.User;
import com.pranshu.smartcontactmanager.repository.UserRepo;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User registerUser(User user, boolean agreement) throws Exception {
        if (!agreement) {
            throw new Exception("You must agree with our terms and conditions.");
        }

        try {
            user.setEnabled(true);
            user.setRole("ROLE_USER");
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

            return userRepo.save(user);
        } catch (Exception e) {
            throw new Exception("Email already exists");
        }
    }
}