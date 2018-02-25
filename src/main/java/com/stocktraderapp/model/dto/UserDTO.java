package com.stocktraderapp.model.dto;

import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class UserDTO {

    private Long id;
    private String email;
    private String name;
    private String city;
    private Date registeredAt;

}
