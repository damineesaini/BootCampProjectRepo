package com.bootcamp.BootcampProject.repository;

import com.bootcamp.BootcampProject.entity.product.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ProductRepository extends CrudRepository<Product, UUID> {
}
