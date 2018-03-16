package com.raven.service;

import com.raven.model.User;
import com.raven.web.dto.UserDetailsDTO;
import com.raven.web.dto.UserInfoDTO;
import com.raven.web.dto.UserProfileDTO;
import com.raven.web.dto.UserRegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserTransformService {

    public UserInfoDTO transformToUserInfoDTO(User user) {

        return UserInfoDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getFullName())
                .office(user.getOffice() == null ? null : user.getOffice().getName())
                .registeredAt(user.getRegisteredAt())
                .build();
    }

    public UserDetailsDTO transformToUserDetailsDTO(User user) {

        return UserDetailsDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getFullName())
                .office(user.getOffice() == null ? null : user.getOffice().getName())
                .registeredAt(user.getRegisteredAt())
                .posts(user.getPostList())
                .build();
    }

    public User transformRegToUser(UserRegistrationDTO userRegDto, PasswordEncoder passwordEncoder) {

        return new User(userRegDto.getEmail(),
                passwordEncoder.encode(userRegDto.getPassword()),
                userRegDto.getGivenName(),
                userRegDto.getMiddleName(),
                userRegDto.getFamilyName()
        );
    }

    public UserProfileDTO transformToUserProfileDTO(User user) {

        return UserProfileDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .officeName(user.getOffice() != null ? user.getOffice().getName() : null)
                .givenName(user.getGivenName())
                .middleName(user.getMiddleName())
                .familyName(user.getFamilyName())
                .registeredAt(user.getRegisteredAt())
                .roles(user.getRoles().stream().map(role -> role.getRoleType().toString()).collect(Collectors.toList()))
                .build();
    }

    public User transformProfToUser(UserProfileDTO userProfDto) {

        return new User(userProfDto.getGivenName(),
                userProfDto.getMiddleName().isEmpty() ? null : userProfDto.getMiddleName(),
                userProfDto.getFamilyName()
        );
    }

}
