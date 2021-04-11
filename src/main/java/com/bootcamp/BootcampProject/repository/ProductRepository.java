package com.bootcamp.BootcampProject.repository;

import com.bootcamp.BootcampProject.entity.product.Product;
import com.bootcamp.BootcampProject.entity.user.Seller;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ProductRepository extends CrudRepository<Product, UUID> {

    Optional<Product> findByName(String name);

    @Query(value = "select * from product where brand =:brand_name", nativeQuery = true )
    List<Product> findAllByBrand(@Param("brand_name") String brand_name);

    @Query(value = "select * from product where category_id=:category", nativeQuery = true )
    List<Product> findAllByCategoryId(@Param("category") UUID category);

    @Query(value = "Select * from product where is_active=1 And is_delete=0",nativeQuery = true)
    List<Product> findAllNonDeletedActive();

    @Query(value = "Select Distinct brand from product where category_id=:categoryId",nativeQuery = true)
    Set<Object> findAllBrandsByCategoryId(@Param("categoryId") UUID categoryId);

    List<Product> findBySellerUserId(Seller seller);
}
