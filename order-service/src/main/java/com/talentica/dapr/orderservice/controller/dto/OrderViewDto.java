package com.talentica.dapr.orderservice.controller.dto;

import com.talentica.dapr.orderservice.repository.entity.OrderLineItem;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OrderViewDto {
    private String orderId;
    private String restaurantId;
    private List<OrderLineItemViewDto> items;
}
