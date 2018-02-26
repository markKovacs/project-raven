package com.raven.service;

import com.raven.model.User;
import com.raven.model.dto.UserDetailsDTO;
import com.raven.model.dto.UserInfoDTO;
import com.raven.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserInfoDTO> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::transformToUserInfoDTO)
                .collect(Collectors.toList());
    }

    public UserDetailsDTO getUser(String userId) {

        return transformToUserDetailsDTO(userRepository.findOne(Long.valueOf(userId)));
    }

    private UserInfoDTO transformToUserInfoDTO(User user) {

        return UserInfoDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getFullName())
                .city(user.getCity().getCityName())
                .registeredAt(user.getRegisteredAt())
                .build();
    }

    private UserDetailsDTO transformToUserDetailsDTO(User user) {

        return UserDetailsDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getFullName())
                .city(user.getCity().getCityName())
                .registeredAt(user.getRegisteredAt())
                .posts(user.getPostList())
                .build();
    }

}
