package com.raven.controller;

import com.raven.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/")
    public String getIndexPage() {
        return "index";
    }

    @RequestMapping(value = "/users")
    public String getUsersPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @RequestMapping(value = "/user/{id}")
    public String getUserDetailsPage(Model model, @PathVariable(value = "id") String userId) {
        model.addAttribute("user", userService.getUser(userId));
        return "user_details";
    }

}
