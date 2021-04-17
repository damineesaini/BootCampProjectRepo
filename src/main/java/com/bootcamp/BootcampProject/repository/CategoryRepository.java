package com.bootcamp.BootcampProject.repository;

import com.bootcamp.BootcampProject.entity.product.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends CrudRepository<Category, UUID> {

    @Query(value = "Select * from category where parent_category_id =:parentCategoryId", nativeQuery = true )
    List<Category> findAllByParentId(@Param("parentCategoryId") UUID parentCategoryId);

    Category findByName(String name);
}
