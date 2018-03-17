package com.raven.service;

import com.raven.model.Office;
import com.raven.model.Role;
import com.raven.model.User;
import com.raven.repository.OfficeRepository;
import com.raven.repository.RoleRepository;
import com.raven.repository.UserRepository;
import com.raven.web.dto.UserDetailsDTO;
import com.raven.web.dto.UserInfoDTO;
import com.raven.web.dto.UserProfileDTO;
import com.raven.web.dto.UserRegistrationDTO;
import com.raven.web.exception.EmailExistsException;
import com.raven.web.exception.OfficeNotExistingException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    @Autowired
    private MailService mailService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private OfficeRepository officeRepository;
    @Autowired
    private UserTransformService userTransformService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserInfoDTO> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(user -> userTransformService.transformToUserInfoDTO(user))
                .collect(Collectors.toList());
    }

    public UserDetailsDTO getUserById(String userId) {

        return userTransformService.transformToUserDetailsDTO(userRepository.findOne(Long.valueOf(userId)));
    }

    public UserProfileDTO getUserByEmail(String email) {

        return userTransformService.transformToUserProfileDTO(userRepository.findByEmail(email));
    }

    @Transactional(rollbackFor = EmailExistsException.class)
    public void register(UserRegistrationDTO userRegDto) throws EmailExistsException {

        User userWithEmail = userRepository.findByEmail(userRegDto.getEmail());

        if (userWithEmail != null) {
            log.info("Registration failed. Email already registered: '" + userRegDto.getEmail() + "'.");
            throw new EmailExistsException();
        }

        User newUser = userTransformService.transformRegToUser(userRegDto, passwordEncoder);

        String activation = UUID.randomUUID().toString();
        newUser.setActivation(activation);

        Role userRole = roleRepository.findByRoleType(Role.RoleType.USER);
        newUser.addRole(userRole);

        try {
            userRepository.save(newUser);
        } catch (DataIntegrityViolationException e) {
            log.info("Registration failed. Email already registered: '" + userRegDto.getEmail() + "'.");
            throw new EmailExistsException();
        }

        log.info("User with email '" + userRegDto.getEmail() + "' has registered.");

        mailService.sendEmail(userRegDto.getEmail(), userRegDto.getGivenName(), activation, MailService.EmailType.ACTIVATION);
    }

    @Transactional
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

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void update(UserProfileDTO userProfDto, String principalEmail) throws OfficeNotExistingException {

        User user;
        Office office = null;

        if (!userProfDto.getOfficeName().isEmpty()) {
            office = officeRepository.findByName(userProfDto.getOfficeName());
            if (office == null) {
                log.info("Update failed. Office '" + userProfDto.getOfficeName() + "' is invalid.");
                throw new OfficeNotExistingException();
            }
        }

        user = userTransformService.transformProfToUser(userProfDto);
        user.setOffice(office);

        userRepository.updateProfile(principalEmail, user.getFamilyName(),
                user.getMiddleName(), user.getGivenName(), user.getOffice());

        log.info("User with email '" + principalEmail + "' updated successfully.");
    }

}
