package com.bootcamp.BootcampProject.repository;

import com.bootcamp.BootcampProject.entity.order.Cart;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends CrudRepository<Cart, UUID> {

    @Query(value = "Select * from cart where customer_user_id=:id",nativeQuery = true)
    List<Cart> findAllByCustomerId(@Param("id") UUID id);

    @Modifying
    @Query(value = "delete from cart where customer_user_id=:id and product_variation_id=:id1",nativeQuery = true)
    void deleteByCustomerIdAndProductVariationId(@Param("id") UUID id,@Param("id1") UUID id1);

    @Query(value = "Select * from cart where customer_user_id=:id and product_variation_id=:id1",nativeQuery = true)
    Optional<Cart> findByCustomerIdAndProductVariationId(@Param("id") UUID id,@Param("id1") UUID id1);
}
