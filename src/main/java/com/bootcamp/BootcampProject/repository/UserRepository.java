package com.bootcamp.BootcampProject.repository;

import com.bootcamp.BootcampProject.entity.user.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
    @Query(value = "select * from user where email=:email",nativeQuery = true)
    User findByEmail(@Param("email") String email);
    @Query("UPDATE User u SET u.loginAttempts = ?1 WHERE u.email = ?2")
    @Modifying
    void updateLoginAttempts(int newLoginAttempts, String email);
}