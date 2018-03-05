package com.raven.web.validation;

import com.raven.web.dto.UserRegistrationDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserRegistrationDTO> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(UserRegistrationDTO user, ConstraintValidatorContext context) {
        return user.getPassword().equals(user.getPasswordConfirmation());
    }

}
