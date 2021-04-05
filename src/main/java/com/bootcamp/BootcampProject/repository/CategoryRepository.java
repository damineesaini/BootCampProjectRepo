package com.bootcamp.BootcampProject.repository;

import com.bootcamp.BootcampProject.entity.product.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface CategoryRepository extends CrudRepository<Category, UUID> {
}
