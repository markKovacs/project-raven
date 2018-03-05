package com.raven.web.controller;

import com.raven.web.dto.UserRegistrationDTO;
import com.raven.web.exception.EmailExistsException;
import com.raven.web.exception.InvalidActivationCodeException;
import com.raven.web.validation.UserRegValidator;
import com.raven.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @InitBinder("userRegDto")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(new UserRegValidator());
    }

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
        model.addAttribute("userRegDto", new UserRegistrationDTO());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String handleRegistration(@ModelAttribute(value = "userRegDto") @Valid UserRegistrationDTO userRegDto,
                                     BindingResult result) {

        if (!result.hasErrors()) {
            try {
                userService.register(userRegDto);
                return "redirect:/login?registered";
            } catch (EmailExistsException e) {
                result.rejectValue("email", "message.regError");
            }
        }

        return "registration";
    }

    @RequestMapping(value = "/activation/{activation}")
    public String handleActivation(@PathVariable(value = "activation") String givenActivation) {

        boolean validActivation = userService.activate(givenActivation);

        if (!validActivation) {
            throw new InvalidActivationCodeException("Invalid activation code: " + givenActivation);
        }

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
