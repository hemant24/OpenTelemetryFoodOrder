package com.talentica.dapr.restaurantservice.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;


@Setter
@Getter
@Entity
public class Restaurant {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String restaurantId;
  @OneToMany(mappedBy="restaurant", cascade = CascadeType.ALL)
  private List<MenuItem> menuItems;
  private int rating;

  private String name;

  public Restaurant() {
    restaurantId = UUID.randomUUID().toString();
  }

}
