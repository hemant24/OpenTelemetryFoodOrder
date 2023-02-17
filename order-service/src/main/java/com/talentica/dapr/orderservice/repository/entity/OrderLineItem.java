package com.talentica.dapr.orderservice.repository.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Setter
@Getter
@Entity
public class OrderLineItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  @JoinColumn(name="order_id", nullable=false)
  private Order order;

  public OrderLineItem() {
  }

  private int quantity;
  private String menuItemId;
  private String name;

  private BigDecimal price;


  public OrderLineItem(String menuItemId, String name, BigDecimal price, int quantity) {
    this.menuItemId = menuItemId;
    this.name = name;
    this.price = price;
    this.quantity = quantity;
  }

  public BigDecimal getTotal() {
    return price.multiply(new BigDecimal(quantity));
  }

}
