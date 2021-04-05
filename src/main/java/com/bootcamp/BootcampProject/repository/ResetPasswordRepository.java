package com.bootcamp.BootcampProject.repository;

import com.bootcamp.BootcampProject.entity.token.ResetPasswordToken;
import com.bootcamp.BootcampProject.entity.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ResetPasswordRepository extends CrudRepository<ResetPasswordToken, UUID> {
    ResetPasswordToken findByUser(User user);

    ResetPasswordToken findByResetToken(String resetToken);
}
