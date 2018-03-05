package com.raven.web.validation;

import com.raven.web.dto.UserRegistrationDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class UserRegValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return UserRegistrationDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(final Object obj, final Errors errors) {
        // obj could be casted to UserRegistrationDTO and validate all fields
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "givenName", "message.givenName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "familyName", "message.familyName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "message.password");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "message.email");
    }

}
