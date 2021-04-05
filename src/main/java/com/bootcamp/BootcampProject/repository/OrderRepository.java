package com.bootcamp.BootcampProject.repository;

import com.bootcamp.BootcampProject.entity.order.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface OrderRepository extends CrudRepository<Order, UUID> {
}
