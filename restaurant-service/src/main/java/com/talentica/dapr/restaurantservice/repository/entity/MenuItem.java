package com.talentica.dapr.restaurantservice.repository.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;


@Setter
@Getter
@Entity
public class MenuItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String menuItemId;
  private String name;
  @Enumerated(EnumType.STRING)
  private FoodType foodType;
  private BigDecimal price;
  @JoinColumn(name="restaurant_id", nullable=false)
  @ManyToOne
  private Restaurant restaurant;

  public enum FoodType {
    VEG, NON_VEG, VEGAN;
  }

  public MenuItem(){
    this.menuItemId = UUID.randomUUID().toString();
  }

}
