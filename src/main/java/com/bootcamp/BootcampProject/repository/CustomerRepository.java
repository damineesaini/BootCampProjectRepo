package com.bootcamp.BootcampProject.repository;

import com.bootcamp.BootcampProject.entity.user.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, UUID> {
//    Customer findBy(User userid);
}
