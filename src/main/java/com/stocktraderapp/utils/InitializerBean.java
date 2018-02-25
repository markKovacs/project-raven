package com.stocktraderapp.utils;

import com.stocktraderapp.model.City;
import com.stocktraderapp.model.User;
import com.stocktraderapp.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class InitializerBean {

    public InitializerBean(UserRepository userRepository) {
        userRepository.save(new User("johndoe@gmail.com", "John", null, "Doe", City.BDP));
        userRepository.save(new User("maryjane@gmail.com", "Mary", "Rose", "Jane", City.WRS));
    }

}