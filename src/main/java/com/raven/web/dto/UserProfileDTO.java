package com.raven.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO {

    // Immutable fields
    private Long id;
    private String email;
    private List<String> roles;
    @DateTimeFormat // transforms String coming from form to Date
    private Date registeredAt;

    // Mutable fields
    @NotNull(message = "{Size.user.givenName}")
    @Size(max = 60, message = "{Size.user.givenName}")
    private String givenName;

    @Size(max = 60, message = "{Size.user.middleName}")
    private String middleName;

    @NotNull(message = "{Size.user.familyName}")
    @Size(max = 60, message = "{Size.user.familyName}")
    private String familyName;

    private String officeName;

}
