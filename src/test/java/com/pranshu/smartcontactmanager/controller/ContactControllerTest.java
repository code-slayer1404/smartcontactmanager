package com.pranshu.smartcontactmanager.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.pranshu.smartcontactmanager.entity.Contact;
import com.pranshu.smartcontactmanager.entity.User;
import com.pranshu.smartcontactmanager.helper.Message;
import com.pranshu.smartcontactmanager.repository.UserRepo;
import com.pranshu.smartcontactmanager.service.UserContactServices;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContactControllerTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private UserContactServices userContactServices;

    @InjectMocks
    private ContactController userController;

    @Mock
    Model model;

    @BeforeEach
    public void init(){
        // Prepare data for the test
        // model = mock(Model.class);

        // Mocking the user attribute added to the model
        lenient().when(model.getAttribute("user")).thenReturn(new User()); // or any appropriate User object
    }

    @SuppressWarnings("null")
    @Test
    public void testOpenAddContact() {
        

        // Call the method being tested
        String viewName = userController.openAddContact(model);

        // Verify that the correct attributes are added to the model
        verify(model, times(1)).addAttribute(eq("contact"), any(Contact.class));

        // Verify that the view name returned by the handler is correct
        assertEquals("normal/add-contact-form", viewName);
        
        assertNotNull(model.getAttribute("user"));
    }

    @SuppressWarnings("null")
    @Test
    public void testProcessAddContact() {
        // Prepare test data
        Contact contact = new Contact();
        MultipartFile multipartFile = mock(MultipartFile.class);
        Message message = new Message("test-success","Contact added successfully");

        // Mock behavior
        when(userContactServices.addContact(any(User.class), eq(contact), any(MultipartFile.class))).thenReturn(message);

        // Call the method being tested
        String viewName = userController.processAddContact(contact, model, multipartFile);

        // Verify interactions
        verify(model, times(1)).addAttribute(eq("message"), eq(message));

        // Verify the view name returned by the handler
        assertEquals("normal/add-contact-form", viewName);
    }
}
