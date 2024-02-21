package com.pranshu.smartcontactmanager.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pranshu.smartcontactmanager.entity.Contact;
import com.pranshu.smartcontactmanager.entity.User;

public interface ContactRepo extends JpaRepository<Contact, Integer> {

    @Query("select c from Contact c where c.user.id=:userId")
    public Page<Contact> getContactsByUserId(@Param("userId") Integer userId,Pageable pageable);


    public List<Contact> findByNameContainingAndUser(String name,User user);
}
