package com.pranshu.smartcontactmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.pranshu.smartcontactmanager.entity.User;
import com.pranshu.smartcontactmanager.helper.Message;
import com.pranshu.smartcontactmanager.service.UserService;

import jakarta.validation.Valid;

@Controller
public class MainController {


    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String home() {
        return "home";
    }

    @RequestMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "sign-up-form";
    }

    @PostMapping("/register")
    public String register(Model model, @Valid @ModelAttribute User user, BindingResult bindingResult,
            @RequestParam(value = "agreement", defaultValue = "false") boolean agreement) {

        try {
            if (bindingResult.hasErrors()) {
                return "sign-up-form";
            }

            User savedUser = userService.registerUser(user, agreement);
            System.out.println(savedUser);
            model.addAttribute("user", new User());
            model.addAttribute("message", new Message("alert-success", "Account created successfully!"));

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", new Message("alert-danger", "Something went wrong!" + e.getMessage()));
        }

        return "sign-up-form";
    }

    @RequestMapping("/login")
    public String signin() {
        return "loginpage";
    }



    // *************************Test controllers************************************
    // */

    @RequestMapping("/testopen")
    public String openUploadform() {
        return "testuploadform";
    }

}
