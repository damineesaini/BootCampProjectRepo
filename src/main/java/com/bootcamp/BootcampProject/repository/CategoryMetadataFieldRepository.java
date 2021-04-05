package com.bootcamp.BootcampProject.repository;

import com.bootcamp.BootcampProject.entity.product.CategoryMetadataField;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface CategoryMetadataFieldRepository extends CrudRepository<CategoryMetadataField, UUID> {
    CategoryMetadataField findByName(String name);
}
