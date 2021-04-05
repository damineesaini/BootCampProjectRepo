package com.bootcamp.BootcampProject.repository;

import com.bootcamp.BootcampProject.entity.user.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface RoleRepository extends CrudRepository<Role, UUID> {
    Role findByAuthority(String authority);
}
