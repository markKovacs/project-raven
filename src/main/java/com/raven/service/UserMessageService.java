package com.raven.service;

import com.raven.model.UserMessage;
import com.raven.repository.UserMessageRepository;
import com.raven.web.messaging.NewsFeedMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMessageService {

    @Autowired
    private UserMessageRepository userMessageRepository;

    public List<NewsFeedMessage> getAllMessages() {
        return userMessageRepository.findAll()
                .stream()
                .map(this::transformMessage)
                .collect(Collectors.toList());
    }

    private NewsFeedMessage transformMessage(UserMessage msg) {
        return new NewsFeedMessage(
                msg.getAuthor().getEmail(),
                msg.getContent(),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(msg.getPostedAt()));
    }

}
