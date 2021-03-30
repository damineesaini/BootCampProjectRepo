package com.bootcamp.BootcampProject.repository;

import com.bootcamp.BootcampProject.entity.token.ConfirmationToken;
import com.bootcamp.BootcampProject.entity.user.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, UUID> {
    ConfirmationToken findByConfirmationToken(String confirmationToken);

    @Modifying
    @Query(value = "delete from confirmation_token_table where confirmation_token =: token",nativeQuery = true)
    void deleteConfirmationToken(@Param("token")String token);

    ConfirmationToken findBYuSER(User user);
}
