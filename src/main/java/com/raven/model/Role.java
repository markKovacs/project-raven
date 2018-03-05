package com.raven.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "users")
@EqualsAndHashCode(of = "roleType")
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(unique = true, nullable = false)
    private RoleType roleType;

    @ManyToMany(mappedBy = "roles", cascade = CascadeType.PERSIST)
    private Set<User> users = new HashSet<>();

    public Role(String roleType) {
        this.roleType = RoleType.valueOf(roleType);
    }

    public enum RoleType {
        USER, ADMIN
    }

}
