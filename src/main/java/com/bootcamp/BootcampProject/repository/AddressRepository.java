package com.bootcamp.BootcampProject.repository;

import com.bootcamp.BootcampProject.entity.user.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface AddressRepository extends CrudRepository<Address, UUID> {
}
