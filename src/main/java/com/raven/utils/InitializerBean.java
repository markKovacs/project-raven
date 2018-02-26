package com.raven.utils;

import com.raven.model.City;
import com.raven.model.Post;
import com.raven.model.User;
import com.raven.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class InitializerBean {

    public InitializerBean(UserRepository userRepository) {
        User johnDoe = new User("johndoe@gmail.com", "John", null, "Doe", City.BDP);
        User maryJane = new User("maryjane@gmail.com", "Mary", "Rose", "Jane", City.WRS);
        User billSmith = new User("billsmith@gmail.com", "Bill", null, "Smith", City.KRK);

        johnDoe.addPost(new Post("A very good content for this post."));
        johnDoe.addPost(new Post("A bit worse content here."));
        johnDoe.addPost(new Post("Generating some cool content."));
        maryJane.addPost(new Post("I like to write for nothing."));
        maryJane.addPost(new Post("Typing in vain, what a pleasure."));

        userRepository.save(johnDoe);
        userRepository.save(maryJane);
        userRepository.save(billSmith);
    }

}