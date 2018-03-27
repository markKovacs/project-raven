package com.raven.web.controller;

import com.raven.service.UserMessageService;
import com.raven.service.UserService;
import com.raven.web.messaging.IncomingMessage;
import com.raven.web.messaging.NewsFeedMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class NewsFeedController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMessageService userMessageService;

    @MessageMapping("/hello")
    @SendTo("/topic/newsfeed")
    public NewsFeedMessage newsFeedMessage(IncomingMessage incomingMessage, Principal principal) {

        String datetimeString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return new NewsFeedMessage(principal.getName(), incomingMessage.getContent(), datetimeString);
    }

    @SubscribeMapping("/subs")
    public ResponseEntity<String> newSubscription(Principal principal) {

        return ResponseEntity.ok("SERVER: " + principal.getName() + " has subscribed.");
    }

    @RequestMapping("/newsfeed")
    public String getNewsFeedPage(Model model) {

        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("userMessages", userMessageService.getAllMessages());

        return "newsfeed";
    }

}
