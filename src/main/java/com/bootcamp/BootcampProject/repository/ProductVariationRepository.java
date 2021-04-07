package com.bootcamp.BootcampProject.repository;

import com.bootcamp.BootcampProject.entity.product.Product;
import com.bootcamp.BootcampProject.entity.product.ProductVariation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductVariationRepository extends CrudRepository<ProductVariation, UUID> {
    List<ProductVariation> findAllByProductId(Product product);
}
