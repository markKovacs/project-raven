package com.raven.repository;

import com.raven.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();
    User findOne(Long id);
    User findByEmail(String email);
    User findOneByActivation(String activation);

}
