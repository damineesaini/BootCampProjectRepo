package com.bootcamp.BootcampProject.repository;

import com.bootcamp.BootcampProject.entity.product.CategoryMetadataFieldValues;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryMetadataFieldValuesRepository extends CrudRepository<CategoryMetadataFieldValues, UUID> {
    @Query(value = "Select * from category_metadata_field_value where category_id =:categoryId AND category_metadata_field_id =:fieldId",nativeQuery = true)
    List<CategoryMetadataFieldValues> findByCategoryIdAndCategoryMetadataFieldId(@Param("categoryId") UUID categoryId, @Param("fieldId") UUID fieldId);

    @Query(value = "Select * from category_metadata_field_value where category_id =:categoryId AND category_metadata_field_id =:fieldId",nativeQuery = true)
    CategoryMetadataFieldValues findItByCategoryIdAndCategoryMetadataFieldId(@Param("categoryId") UUID categoryId, @Param("fieldId") UUID fieldId);

    @Query(value = "Select * from category_metadata_field_value where category_id =:categoryId",nativeQuery = true)
    List<CategoryMetadataFieldValues> findByCategoryId(@Param("categoryId") UUID categoryId);
}
