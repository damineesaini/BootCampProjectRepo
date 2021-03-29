package com.bootcamp.BootcampProject.repository;

import com.bootcamp.BootcampProject.entity.product.CategoryMetadataFieldValues;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface CategoryMetadataFieldValuesRepository extends CrudRepository<CategoryMetadataFieldValues, UUID> {
}
