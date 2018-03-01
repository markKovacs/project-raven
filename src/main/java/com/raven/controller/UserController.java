package com.raven.controller;

import com.raven.model.exception.InvalidActivationCodeException;
import com.raven.model.dto.UserRegistrationDTO;
import com.raven.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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

    @RequestMapping(value = "/registration")
    public String getRegistrationPage(Model model) {
        model.addAttribute("user", new UserRegistrationDTO());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String handleRegistration(@ModelAttribute UserRegistrationDTO user, Model model) {

        List<String> errors = userService.register(user);

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            model.addAttribute("user", user);
            return "registration";
        }

        return "redirect:/login?registered";
    }

    @RequestMapping(value = "/activation/{activation}")
    public String handleActivation(@PathVariable(value = "activation") String givenActivation) {

        boolean validActivation = userService.activate(givenActivation);

        if (!validActivation) {
            throw new InvalidActivationCodeException("Invalid activation code: " + givenActivation);
        }

        // TODO: 3/1/18 get email from userService.activate(givenActivation) and display in message
        return "redirect:/login?activated";
    }

    @ExceptionHandler(value = InvalidActivationCodeException.class)
    public ModelAndView onInvalidActivationCodeException(Exception e) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("errorTitle", "Invalid Activation Code");
        mav.addObject("errorMessage", e.getMessage() + ". Please contact the server admin.");
        mav.setViewName("errors/simple_error");
        return mav;
    }

}
