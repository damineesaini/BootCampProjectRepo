package com.bootcamp.BootcampProject.repository;

import com.bootcamp.BootcampProject.entity.user.Address;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface AddressRepository extends CrudRepository<Address, UUID> {

    @Modifying
    @Query(value = "delete from address where user_id =:id and id=:addressId",nativeQuery = true)
    void deleteAddress(@Param("id") UUID id,@Param("addressId") UUID addressId);
//    @Modifying
//    @Query(value = "delete from address where user_id =:id and id=:addressId",nativeQuery = true)
//    void deleteAddress(@Param("id") UUID id, @Param("addressId")UUID addressId);
}
