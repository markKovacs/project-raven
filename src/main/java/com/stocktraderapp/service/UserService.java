package com.stocktraderapp.service;

import com.stocktraderapp.model.User;
import com.stocktraderapp.model.dto.UserDTO;
import com.stocktraderapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public List<UserDTO> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::transform)
                .collect(Collectors.toList());
    }

    private UserDTO transform(User user) {

        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getFullName())
                .city(user.getCity().getCityName())
                .registeredAt(user.getRegisteredAt())
                .build();
    }

}
