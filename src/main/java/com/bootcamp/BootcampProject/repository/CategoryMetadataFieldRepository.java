package com.bootcamp.BootcampProject.repository;

import com.bootcamp.BootcampProject.entity.product.CategoryMetadataField;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryMetadataFieldRepository extends CrudRepository<CategoryMetadataField, UUID> {

    CategoryMetadataField findByName(String name);

    @Query(value = "Select * from category_metadata_field where name =:field",nativeQuery = true)
    CategoryMetadataField findIdByName(@Param("field") String field);

}
