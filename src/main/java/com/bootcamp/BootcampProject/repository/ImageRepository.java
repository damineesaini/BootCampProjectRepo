package com.bootcamp.BootcampProject.repository;

import com.bootcamp.BootcampProject.entity.image.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ImageRepository extends CrudRepository<Image, UUID> {
}
