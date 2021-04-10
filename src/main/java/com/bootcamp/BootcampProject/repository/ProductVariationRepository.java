package com.bootcamp.BootcampProject.repository;

import com.bootcamp.BootcampProject.entity.product.Product;
import com.bootcamp.BootcampProject.entity.product.ProductVariation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ProductVariationRepository extends CrudRepository<ProductVariation, UUID> {
    List<ProductVariation> findAllByProductId(Product product);

    @Query("select min(price) from ProductVariation where product_id =:categoryId")
    double findMinimumPrice(@Param("categoryId") UUID categoryId);

    @Query("select max(price) from ProductVariation where product_id =:categoryId")
    double findMaximumPrice(@Param("categoryId") UUID categoryId);

    @Query(value = "Select * From product_variation where price=:minPrice OR price =:maxPrice",nativeQuery = true)
    Set<ProductVariation> findMaxAndMinPrice(@Param("minPrice") double minPrice , @Param("maxPrice")double maxPrice);
}
