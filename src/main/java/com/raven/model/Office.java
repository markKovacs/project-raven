package com.raven.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(exclude = "users")
@Entity
public class Office {

    @Id
    private String id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "office")
    private List<User> users = new ArrayList<>();

    public Office(String id, String name) {
        this.id = id;
        this.name = name;
    }

}
