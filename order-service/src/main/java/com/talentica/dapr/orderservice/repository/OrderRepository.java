package com.talentica.dapr.orderservice.repository;


import com.talentica.dapr.orderservice.repository.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

  Order findByOrderId(String id);

}
