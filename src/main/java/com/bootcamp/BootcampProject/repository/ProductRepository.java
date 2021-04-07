package com.bootcamp.BootcampProject.repository;

import com.bootcamp.BootcampProject.entity.product.Category;
import com.bootcamp.BootcampProject.entity.product.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends CrudRepository<Product, UUID> {

    Optional<Product> findByName(String name);

    List<Product> findAllByBrand(String brand);

    List<Product> findAllByCategoryId(Category category);

}
