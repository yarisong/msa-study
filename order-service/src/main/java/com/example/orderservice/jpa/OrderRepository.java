package com.example.orderservice.jpa;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<OrderEntity, Long> {

    OrderEntity findByOrderId(String orderId);

    Iterable<OrderEntity> findByUserId(String userId);
}
