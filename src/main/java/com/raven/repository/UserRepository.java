package com.raven.repository;

import com.raven.model.Office;
import com.raven.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();

    User findOne(Long id);

    User findByEmail(String email);

    User findOneByActivation(String activation);

    @Modifying
    @Query(value = "UPDATE User u " +
            "SET u.givenName = :givenName, " +
            "u.middleName = :middleName, " +
            "u.familyName = :familyName, " +
            "u.office = :office " +
            "WHERE u.email = :email")
    void updateProfile(@Param("email") String email,
                       @Param("familyName") String familyName,
                       @Param("middleName") String middleName,
                       @Param("givenName") String givenName,
                       @Param("office") Office office);

}
