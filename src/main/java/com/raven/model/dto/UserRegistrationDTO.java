package com.raven.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDTO {

    private String email;
    private String password;
    private String passwordConfirmation;

    @NotNull(message = "Given name is required.")
    @Size(min = 1, message = "{Size.userDto.firstName}")
    private String givenName;

    private String middleName;

    @NotNull
    @Size(min = 1, message = "{Size.userDto.firstName}")
    private String familyName;

}
