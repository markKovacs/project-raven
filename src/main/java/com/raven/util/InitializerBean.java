package com.raven.util;

import com.raven.model.Office;
import com.raven.model.Post;
import com.raven.model.Role;
import com.raven.model.User;
import com.raven.repository.OfficeRepository;
import com.raven.repository.RoleRepository;
import org.springframework.stereotype.Component;

@Component
public class InitializerBean {

    public InitializerBean(RoleRepository roleRepository, OfficeRepository officeRepository) {

        Office newYork = new Office("NY", "New York");
        Office sanFrancisco = new Office("SF", "San Francisco");
        Office london = new Office("LO", "London");
        Office budaPest = new Office("BP", "Budapest");

        officeRepository.save(newYork);
        officeRepository.save(sanFrancisco);
        officeRepository.save(budaPest);
        officeRepository.save(london);

        User user = new User("user@raven.com", "Mark", null, "Smith", budaPest, "$2a$11$sCVwOuB1ypaBeZDJQDH2RexYlqXQ6EOb1N7bhJTWoRfNxtIYCXqvi");
        User johnDoe = new User("johndoe@raven.com", "John", null, "Doe", newYork, "$2a$11$sCVwOuB1ypaBeZDJQDH2RexYlqXQ6EOb1N7bhJTWoRfNxtIYCXqvi");
        User maryJane = new User("maryjane@raven.com", "Mary", "Rose", "Jane", newYork, "$2a$11$sCVwOuB1ypaBeZDJQDH2RexYlqXQ6EOb1N7bhJTWoRfNxtIYCXqvi");
        User billSmith = new User("billsmith@raven.com", "Bill", null, "Smith", null, "$2a$11$sCVwOuB1ypaBeZDJQDH2RexYlqXQ6EOb1N7bhJTWoRfNxtIYCXqvi");

        johnDoe.addPost(new Post("A very good content for this post."));
        johnDoe.addPost(new Post("A bit worse content here."));
        johnDoe.addPost(new Post("Generating some cool content."));
        maryJane.addPost(new Post("I like to write for nothing."));
        maryJane.addPost(new Post("Typing in vain, what a pleasure."));

        Role userRole = new Role("USER");
        Role adminRole = new Role("ADMIN");

        johnDoe.addRoles(userRole, adminRole);
        maryJane.addRoles(userRole, adminRole);
        billSmith.addRoles(userRole);
        user.addRoles(userRole, adminRole);

        // Everything persisted through cascades
        roleRepository.save(adminRole);
        roleRepository.save(userRole);

    }

}