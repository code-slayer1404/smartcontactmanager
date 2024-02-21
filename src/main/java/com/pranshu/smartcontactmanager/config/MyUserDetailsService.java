package com.pranshu.smartcontactmanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.pranshu.smartcontactmanager.entity.User;
import com.pranshu.smartcontactmanager.repository.UserRepo;

public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.getUserByUsername(username);
        //VVIP
        if (user == null) {
            throw new UsernameNotFoundException("User not found" + username);
        }
        return new MyUserDetails(user);
    }

}
