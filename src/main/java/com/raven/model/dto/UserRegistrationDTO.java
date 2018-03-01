package com.raven.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDTO {

    private String email;
    private String password;
    private String passwordConfirmation;
    private String givenName;
    private String middleName;
    private String familyName;

}
