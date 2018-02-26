package com.raven.model.dto;

import com.raven.model.Post;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class UserDetailsDTO {

    private Long id;
    private String email;
    private String name;
    private String city;
    private Date registeredAt;
    private List<Post> posts;

}
