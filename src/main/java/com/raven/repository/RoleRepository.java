package com.raven.repository;

import com.raven.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleType(Role.RoleType roleType);

}
