package com.raven.web.dto;

import com.raven.web.validation.PasswordMatches;
import com.raven.web.validation.ValidEmail;
import com.raven.web.validation.ValidPassword;
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
@PasswordMatches(message = "{PasswordMatches.user.password}")
public class UserRegistrationDTO {

    @NotNull
    @ValidEmail(message = "{ValidEmail.user.email}")
    private String email;

    @NotNull
    @ValidPassword(message = "{ValidPassword.user.password}")
    private String password;

    private String passwordConfirmation;

    @NotNull
    @Size(max = 60, message = "{Size.userRegDto.givenName}")
    private String givenName;

    @Size(max = 60, message = "{Size.userRegDto.middleName}")
    private String middleName;

    @NotNull
    @Size(max = 60, message = "{Size.userRegDto.familyName}")
    private String familyName;

}
