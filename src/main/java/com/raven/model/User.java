package com.raven.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String givenName;
    private String middleName;
    private String familyName;

    @Enumerated(value = EnumType.STRING)
    private City city;

    @Temporal(TemporalType.TIMESTAMP)
    private Date registeredAt;

    @Column(length = 80)
    private String userHash;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    @OrderBy("postedAt desc NULLS LAST")
    private List<Post> postList = new ArrayList<>();

    public User(String email, String givenName, String middleName, String familyName, City city) {
        this.email = email;
        this.givenName = givenName;
        this.middleName = middleName;
        this.familyName = familyName;
        this.city = city;
        this.registeredAt = new Date();
        this.userHash = UUID.randomUUID().toString();
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

}
