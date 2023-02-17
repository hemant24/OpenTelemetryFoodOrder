package com.talentica.dapr.restaurantservice.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Setter
@Getter
public class MenuItemViewDto {
    private String menuItemId;
    private String name;
    private String foodType;
    private BigDecimal price;
}
