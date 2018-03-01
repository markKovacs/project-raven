package com.raven.service;

import com.raven.model.Role;
import com.raven.model.User;
import com.raven.model.dto.UserDetailsDTO;
import com.raven.model.dto.UserInfoDTO;
import com.raven.model.dto.UserRegistrationDTO;
import com.raven.repository.RoleRepository;
import com.raven.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<String> register(UserRegistrationDTO user) {

        List<String> errors = new ArrayList<>();

        // Registration input validation, accumulate error messages in a list
        validateRegInput(user, errors);

        if (errors.isEmpty()) {
            // Create new User with new activation code and User role
            User newUser = new User(user.getEmail(), passwordEncoder.encode(user.getPassword()),
                    user.getGivenName(), user.getMiddleName(), user.getFamilyName());

            String activation = generateActivation();
            newUser.setActivation(activation);
            newUser.setEnabled(false);

            Role userRole = roleRepository.findByRoleType(Role.RoleType.USER);
            newUser.addRole(userRole);

            // Save new user and send activation email
            userRepository.save(newUser);
            log.info("User with email '" + user.getEmail() + "' has registered.");
            mailService.sendEmail(user.getEmail(), user.getGivenName(), activation, MailService.EmailType.ACTIVATION);
        }

        return errors;
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

    private void validateRegInput(UserRegistrationDTO user, List<String> errors) {
        if (isEmailInvalid(user.getEmail())) {
            errors.add("Email field is invalid.");
        }
        User userWithEmail = userRepository.findByEmail(user.getEmail());
        if (userWithEmail != null) {
            errors.add("Email address is already registered.");
        }
        if (!user.getPassword().equals(user.getPasswordConfirmation())) {
            errors.add("Passwords are not matching");
        } else if (isPasswordInvalid(user.getPassword())) {
            errors.add("Password field is invalid.");
        }
        if (isNameInvalid(user.getGivenName())) {
            errors.add("Given name is invalid.");
        }
        if (isNameInvalid(user.getFamilyName())) {
            errors.add("Family name is invalid.");
        }
        if (user.getMiddleName() != null &&
                !user.getMiddleName().isEmpty() &&
                isNameInvalid(user.getMiddleName())) {
            errors.add("Middle name is invalid.");
        }
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

    private boolean isEmailInvalid(String email) {
        // TODO: 2/27/18 Implement validation logic
        return false;
    }

    private boolean isPasswordInvalid(String password) {
        // TODO: 2/27/18 Implement validation logic
        return false;
    }

    private boolean isNameInvalid(String familyName) {
        // TODO: 2/27/18 Implement validation logic
        return false;
    }

}
