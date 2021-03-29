package com.bootcamp.BootcampProject.repository;

import com.bootcamp.BootcampProject.entity.order.OrderStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderStatusRepository extends CrudRepository<OrderStatus, UUID> {
}
