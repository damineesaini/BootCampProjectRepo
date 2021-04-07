package com.bootcamp.BootcampProject.repository;

import com.bootcamp.BootcampProject.entity.token.UnlockAccountToken;
import com.bootcamp.BootcampProject.entity.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UnlockAccountTokenRepository extends CrudRepository<UnlockAccountToken, UUID> {
    UnlockAccountToken findByUser(User user);

    UnlockAccountToken findByUnlockAccountToken(String unlockAccountToken);
}
