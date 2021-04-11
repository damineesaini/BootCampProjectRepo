package com.bootcamp.BootcampProject.repository;

import com.bootcamp.BootcampProject.entity.user.Seller;
import com.bootcamp.BootcampProject.entity.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SellerRepository extends CrudRepository<Seller, UUID> {
    Seller findByUserId(User user);

    Seller findByCompanyName(String companyName);
}