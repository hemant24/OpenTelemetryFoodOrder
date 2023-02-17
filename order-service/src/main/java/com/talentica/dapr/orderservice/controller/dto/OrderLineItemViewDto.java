package com.talentica.dapr.orderservice.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class OrderLineItemViewDto {
    private int quantity;
    private String menuItemId;
    private String name;

    private BigDecimal price;
}
