package com.raven.service;

import com.raven.model.Role;
import com.raven.model.User;
import com.raven.repository.RoleRepository;
import com.raven.repository.UserRepository;
import com.raven.web.dto.UserDetailsDTO;
import com.raven.web.dto.UserInfoDTO;
import com.raven.web.dto.UserRegistrationDTO;
import com.raven.web.exception.EmailExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public List<UserInfoDTO> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::transformToUserInfoDTO)
                .collect(Collectors.toList());
    }

    public UserDetailsDTO getUser(String userId) {

        return transformToUserDetailsDTO(userRepository.findOne(Long.valueOf(userId)));
    }

    public void register(UserRegistrationDTO userRegDto) throws EmailExistsException {
        User userWithEmail = userRepository.findByEmail(userRegDto.getEmail());

        if (userWithEmail != null) {
            log.info("Registration failed. Email already registered: '" + userRegDto.getEmail() + "'.");
            throw new EmailExistsException();
        }

        User newUser = transformRegToUser(userRegDto);

        String activation = generateActivation();
        newUser.setActivation(activation);

        Role userRole = roleRepository.findByRoleType(Role.RoleType.USER);
        newUser.addRole(userRole);

        userRepository.save(newUser);
        log.info("User with email '" + userRegDto.getEmail() + "' has registered.");

        mailService.sendEmail(userRegDto.getEmail(), userRegDto.getGivenName(), activation, MailService.EmailType.ACTIVATION);
    }

    public boolean activate(String givenActivation) {
        User user = userRepository.findOneByActivation(givenActivation);

        if (user == null) {
            log.warn("Account activation failed. Activation code: " + givenActivation);
            return false;
        }

        user.setEnabled(true);
        user.setActivation(null);

        userRepository.save(user);
        log.info("Account activation with email '" + user.getEmail() + "' was successful.");

        mailService.sendEmail(user.getEmail(), user.getGivenName(), MailService.EmailType.WELCOME);

        return true;
    }

    private String generateActivation() {
        return UUID.randomUUID().toString();
    }

    private UserInfoDTO transformToUserInfoDTO(User user) {

        return UserInfoDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getFullName())
                .city(user.getCity() == null ? null : user.getCity().getCityName())
                .registeredAt(user.getRegisteredAt())
                .build();
    }

    private UserDetailsDTO transformToUserDetailsDTO(User user) {

        return UserDetailsDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getFullName())
                .city(user.getCity() == null ? null : user.getCity().getCityName())
                .registeredAt(user.getRegisteredAt())
                .posts(user.getPostList())
                .build();
    }

    private User transformRegToUser(UserRegistrationDTO userRegDto) {

        return new User(userRegDto.getEmail(),
                passwordEncoder.encode(userRegDto.getPassword()),
                userRegDto.getGivenName(),
                userRegDto.getMiddleName(),
                userRegDto.getFamilyName()
        );
    }

}
