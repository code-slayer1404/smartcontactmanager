package com.pranshu.smartcontactmanager.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.pranshu.smartcontactmanager.entity.Contact;
import com.pranshu.smartcontactmanager.entity.User;
import com.pranshu.smartcontactmanager.repository.ContactRepo;
import com.pranshu.smartcontactmanager.repository.UserRepo;

@RestController
public class ContactAPI {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ContactRepo contactRepo;

    @GetMapping("/search/{query}")
    public ResponseEntity<?> search(@PathVariable("query") String query, Principal principal) {
        
            User user = userRepo.getUserByUsername(principal.getName()); // get logged in user
            List<Contact> matchingContacts = contactRepo.findByNameContainingAndUser(query, user);
            return ResponseEntity.ok(matchingContacts);
    }

}
