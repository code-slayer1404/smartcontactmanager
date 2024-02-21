package com.pranshu.smartcontactmanager.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.pranshu.smartcontactmanager.entity.Contact;
import com.pranshu.smartcontactmanager.entity.User;
import com.pranshu.smartcontactmanager.helper.Message;
import com.pranshu.smartcontactmanager.repository.ContactRepo;
import com.pranshu.smartcontactmanager.repository.UserRepo;
import com.pranshu.smartcontactmanager.service.UserContactServices;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ContactRepo contactRepo;

    @Autowired
    UserContactServices userContactServices;

    // common
    @ModelAttribute
    public void addUserToAllHandlers(Model model, Principal principal) {
        String username = principal.getName();

        User user = userRepo.getUserByUsername(username);

        // now all handlers will have user
        model.addAttribute("user", user);
    }

    // homepage for user
    @RequestMapping("/index")
    public String openDashBoard(Model model, Principal principal) {
        return "normal/dashboard";
    }

    // ***********************create operations********************************** */
    // open add contact form
    @RequestMapping("/open-add-contact")
    public String openAddContact(Model model) {
        model.addAttribute("contact", new Contact());
        return "normal/add-contact-form";
    }

    // process add contact form
    @PostMapping("/add-contact")
    public String processAddContact(@ModelAttribute Contact contact, Model model,
            @RequestParam("profile-image") MultipartFile multipartFile) {

        User user = (User) model.getAttribute("user");
        Message message = userContactServices.addContact(user, contact, multipartFile);
        model.addAttribute("message", message);

        // going back to add contact page
        return "normal/add-contact-form";
    }

    // ********************************Retrieve operations************************
    // */
    // handler to view all contacts
    @RequestMapping("/view-contacts/{pageNumber}")
    public String viewContacts(Model model, Principal principal, @PathVariable(value = "pageNumber") int pageNumber) {

        String username = principal.getName();
        User user = userRepo.getUserByUsername(username);

        Pageable pageable = PageRequest.of(pageNumber, 4);
        Page<Contact> contactsPage = contactRepo.getContactsByUserId(user.getId(), pageable);

        model.addAttribute("contacts", contactsPage); // actual list on current page
        model.addAttribute("currentPage", pageNumber); // current page number
        model.addAttribute("totalPages", contactsPage.getTotalPages()); // total no of pages
        return "normal/view-contacts";
    }

    // handler to open a contact
    @PostMapping("/contact/{cid}")
    public String openContactDetails(@PathVariable(value = "cid") int cid, Model model) {

        Contact contact = userContactServices.openContact(cid);
        model.addAttribute("contact", contact);
        return "normal/open-contact";
    }

    // ***************************Delete Operations****************************/
    // handler to delete a contact
    @RequestMapping("/delete-contact/{cid}")
    public String name(@PathVariable("cid") int cid, Model model, HttpSession session) {

        User user = (User) model.getAttribute("user");
        Message message = userContactServices.deleteContact(user, cid);
        session.setAttribute("message", message);

        return "redirect:/user/view-contacts/0";
    }

    // ************************** Update operations****************************/
    
    @PostMapping("/open-update/{cid}")
    public String updateContact(@PathVariable("cid") int cid, Model model) {
        Contact contact = contactRepo.findById(cid).get();

        model.addAttribute("contact", contact);

        return "normal/update-contact-form";
    }

    @PostMapping("/update-contact")
    public String updateContactProcess(@ModelAttribute("contact") Contact contact,
            @RequestParam("profile-image") MultipartFile multipartFile, HttpSession session) {

        Message message = userContactServices.updateContact(contact, multipartFile);
        session.setAttribute("message", message);

        return "redirect:/user/view-contacts/0";
    }
}
