package com.bootcamp.BootcampProject.repository;

import com.bootcamp.BootcampProject.entity.user.Address;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressRepository extends CrudRepository<Address, UUID> {

    @Query(value = "Select * from address where user_id =:id and is_delete=false",nativeQuery = true)
    List<Address> findAllByUserId(@Param("id") UUID id);

    @Query(value = "Select * from address where user_id =:id and is_delete=false and label=:label",nativeQuery = true)
    Optional<Address> findByUserIdAndLabel(@Param("id") UUID id,@Param("label") String label);
}
