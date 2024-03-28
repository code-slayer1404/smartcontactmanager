package com.pranshu.smartcontactmanager.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pranshu.smartcontactmanager.entity.Contact;
import com.pranshu.smartcontactmanager.entity.User;
import com.pranshu.smartcontactmanager.helper.Message;
import com.pranshu.smartcontactmanager.repository.ContactRepo;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserContactServiceTest {

    @Mock
    private ContactRepo contactRepo;

    @InjectMocks
    private UserContactServices userContactServices;

    @Test
    public void testOpenContact() {
        // Prepare data for the test
        int cid = 1;
        Contact expectedContact = new Contact();
        expectedContact.setCid(cid);
        expectedContact.setName("Test Contact");

        // Mock the behavior of contactRepo.findById()
        when(contactRepo.findById(cid)).thenReturn(Optional.of(expectedContact));

        // Call the method being tested
        Contact result = userContactServices.openContact(cid);

        // Verify that contactRepo.findById() is called with the correct parameter
        verify(contactRepo, times(1)).findById(cid);

        // Verify that the method returns the expected Contact object
        assertEquals(expectedContact, result);
    }

    @Test
    public void testOpenContactNotFound() {
        // Prepare data for the test
        int cid = 1;

        // Mock the behavior of contactRepo.findById() when contact is not found
        when(contactRepo.findById(cid)).thenReturn(Optional.empty());

        // Call the method being tested
        Contact result = userContactServices.openContact(cid);

        // Verify that contactRepo.findById() is called with the correct parameter
        verify(contactRepo, times(1)).findById(cid);

        // Verify that the method returns an empty Contact object
        // I have overwritten the equals method of Contact
        assertEquals(result, new Contact());
    }


    @Test
    public void testDeleteContact_SuccessfulDeletion() {
        // Prepare data for the test
        int cid = 1;
        User user = new User();
        user.setId(1);
        Contact contact = new Contact();
        contact.setCid(cid);
        contact.setUser(user);

        // Mock the behavior of contactRepo.findById()
        when(contactRepo.findById(cid)).thenReturn(Optional.of(contact));

        // Call the method being tested
        Message result = userContactServices.deleteContact(user, cid);

        // Verify that contactRepo.findById() is called with the correct parameter
        verify(contactRepo, times(1)).findById(cid);

        // Verify that the contact is deleted and the success message is returned
        verify(contactRepo, times(1)).delete(contact);
        assertEquals("alert-success", result.getType());
        assertEquals("contact deleted successfully!", result.getContent());
    }

    @Test
    public void testDeleteContact_ContactNotFound() {
        // Prepare data for the test
        int cid = 1;
        User user = new User();
        user.setId(1);

        // Mock the behavior of contactRepo.findById() when contact is not found
        when(contactRepo.findById(cid)).thenReturn(Optional.empty());

        Message message = userContactServices.deleteContact(user, cid);

        assertEquals("something went wrong!", message.getContent());

        // Verify that contactRepo.findById() is called with the correct parameter
        verify(contactRepo, times(1)).findById(cid);
    }

    @Test
    public void testDeleteContact_UnauthorizedDeletion() {
        // Prepare data for the test
        int cid = 1;
        User user = new User();
        user.setId(2); // Different user id from contact owner
        Contact contact = new Contact();
        contact.setCid(cid);
        contact.setUser(new User()); // Assuming contact owner is a different user

        // Mock the behavior of contactRepo.findById()
        when(contactRepo.findById(cid)).thenReturn(Optional.of(contact));


        Message message = userContactServices.deleteContact(user, cid);

        assertEquals("something went wrong!", message.getContent());

        // Verify that contactRepo.findById() is called with the correct parameter
        verify(contactRepo, times(1)).findById(cid);
    }
}
