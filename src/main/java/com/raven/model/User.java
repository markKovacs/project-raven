package com.raven.model;

import lombok.*;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "email")
@ToString
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false, length = 60)
    private String userHash;

    private Boolean enabled;
    private String activation;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> roles = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "office_id", referencedColumnName = "id")
    private Office office;

    private String givenName;
    private String middleName;
    private String familyName;

    @Temporal(TemporalType.TIMESTAMP)
    private Date registeredAt = new Date();

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<Post> postList = new ArrayList<>();

    public User(String email, String givenName, String middleName, String familyName, Office office, String pw) {
        // Used for data initialization, only for testing
        this.email = email;
        this.givenName = givenName;
        this.middleName = middleName;
        this.familyName = familyName;
        this.office = office;
        this.userHash = pw;
        this.enabled = true;
    }

    public User(String email, String userHash,
                String givenName, String middleName, String familyName) {
        // Used as copy constructor, to transform UserRegistrationDTO objects
        this.email = email;
        this.userHash = userHash;
        this.givenName = givenName;
        this.middleName = middleName.isEmpty() ? null : middleName;
        this.familyName = familyName;
        this.enabled = false;
    }

    public User(String givenName, String middleName, String familyName) {
        // Used as a copy constructor, to transform UserProfileDTO objects
        this.givenName = givenName;
        this.middleName = middleName;
        this.familyName = familyName;
    }

    public String getFullName() {
        if (!StringUtils.isEmpty(middleName)) {
            return givenName + " " + middleName + " " + familyName;
        }
        return givenName + " " + familyName;
    }

    public void addPost(Post post) {
        this.postList.add(post);
        post.setUser(this);
    }

    public void removePost(Post post) {
        this.postList.remove(post);
        post.setUser(null);
    }

    public void addRole(Role role) {
        this.roles.add(role);
        role.getUsers().add(this);
    }

    public void addRoles(Role... roles) {
        for (Role role : roles) {
            this.roles.add(role);
            role.getUsers().add(this);
        }
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
        role.getUsers().remove(this);
    }

    public void removeAllRoles() {
        Iterator<Role> itr = this.roles.iterator();
        while (itr.hasNext()) {
            Role role = itr.next();
            role.getUsers().remove(this);
            itr.remove();
        }
    }

}
