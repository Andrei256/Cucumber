package com.cucumber.controller;

import com.cucumber.model.User;
import com.cucumber.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registrationView() {
        return "user/registration";
    }
    @PostMapping("/registration")
    public String addUser(User user, Model model){
        if (userService.addUser(user)) {
            return "redirect:/login";
        }
        model.addAttribute("massage", "Имя пользователя занято");
        return "registration";
    }

    @GetMapping("/all")
    public String showListUsersPage(Model model) {

        return "";
    }
}
