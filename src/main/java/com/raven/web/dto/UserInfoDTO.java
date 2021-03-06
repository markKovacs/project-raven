package com.raven.web.dto;

import lombok.*;

import java.util.Date;

/**
 * Lean DTO version of User class, containing necessary info only for listing.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {

    private Long id;
    private String email;
    private String name;
    private String office;
    private Date registeredAt;

}
