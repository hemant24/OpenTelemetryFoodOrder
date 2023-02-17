package com.talentica.dapr.orderservice.repository.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;


@Setter
@Getter
@Entity
@Table(name="orders")
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String orderId;
  private String restaurantId;
  @OneToMany(mappedBy="order", cascade = CascadeType.ALL)
  private List<OrderLineItem> items;

  public Order(){
    this.orderId = UUID.randomUUID().toString();
  }









}
