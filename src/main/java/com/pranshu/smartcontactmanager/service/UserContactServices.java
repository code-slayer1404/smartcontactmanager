package com.pranshu.smartcontactmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pranshu.smartcontactmanager.entity.Contact;
import com.pranshu.smartcontactmanager.entity.User;
import com.pranshu.smartcontactmanager.helper.Message;
import com.pranshu.smartcontactmanager.repository.ContactRepo;
import com.pranshu.smartcontactmanager.repository.UserRepo;

@Service
public class UserContactServices {

    @Autowired
    private FileServices fileServices;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ContactRepo contactRepo;

    public Message addContact(User user, Contact contact, MultipartFile multipartFile) {
        try {
            if (user == null) {
                throw new Exception("You are not loggged in!");
            }
            if (contact.getName().equals("") && contact.getPhone().equals("") && contact.getEmail().equals("")) {
                throw new Exception("Contact must have at least any one of name,email or phone");
            }

            if (multipartFile != null && (!multipartFile.isEmpty())) {
                fileServices.upload(contact, multipartFile);
            }else{
                contact.setImage("default.jpg");
            }

            user.getContacts().add(contact);
            contact.setUser(user);
            userRepo.save(user);
            return new Message("alert-success", "Contact added successfully.");

        } catch (Exception e) {
            e.printStackTrace();
            return new Message("alert-danger", "Contact was not added");
        }
    }

    public Message updateContact(Contact contact, MultipartFile multipartFile) {
        try {
            // vvip
            Contact actualContact = contactRepo.findById(contact.getCid()).get();

            actualContact.setName(contact.getName());
            actualContact.setPhone(contact.getPhone());
            actualContact.setEmail(contact.getEmail());
            actualContact.setWork(contact.getWork());
            actualContact.setDescription(contact.getDescription());

            if (multipartFile != null && !multipartFile.isEmpty()) {
                // upload new image
                fileServices.upload(actualContact, multipartFile);
            }

            contactRepo.save(actualContact);
            return new Message("alert-success", "Contact updated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message("alert-danger", "Something went wrong!");

        }
    }

    public Message deleteContact(User user, int cid) {
        Contact contact = contactRepo.findById(cid).get();

        try {
            if (contact == null) {
                throw new Exception("This contact does not exist.");
            }
            // verification so user does not delete others contact by changing url
            if (user.getId() == contact.getUser().getId()) {
                contactRepo.delete(contact);
                return new Message("alert-success", "contact deleted successfully!");
            } else {
                throw new Exception("You  do not have permission to perform this action");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message("alert-danger", "something went wrong!");
        }
    }


    public Contact openContact(int cid) {
        Contact contact = contactRepo.findById(cid).orElseGet(() -> new Contact());
        return contact;
    }
}
