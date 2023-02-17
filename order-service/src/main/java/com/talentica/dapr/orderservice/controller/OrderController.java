package com.talentica.dapr.orderservice.controller;

import com.talentica.dapr.orderservice.controller.dto.CreateOrderDto;
import com.talentica.dapr.orderservice.controller.dto.OrderViewDto;
import com.talentica.dapr.orderservice.repository.entity.Order;
import com.talentica.dapr.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/order")
@Slf4j
public class OrderController {

  @Autowired
  OrderService orderService;

  @PostMapping
  CreateOrderDto createOrder(@RequestBody Order order){
    return  new CreateOrderDto(orderService.createOrder(order));
  }

  @PostMapping("/{orderId}/cancel")
  void cancelOrder(@PathVariable("orderId") String orderId){
    orderService.cancelOrder(orderId);
  }

  @GetMapping("/{orderId}")
  OrderViewDto getOrder(@PathVariable("orderId") String orderId) {
    return orderService.getOrder(orderId);
  }
}
