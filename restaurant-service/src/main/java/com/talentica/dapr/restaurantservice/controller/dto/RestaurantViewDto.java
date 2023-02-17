package com.talentica.dapr.restaurantservice.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class RestaurantViewDto {
    private String restaurantId;
    private List<MenuItemViewDto> menuItems;
    private int rating;
    private String name;
}
