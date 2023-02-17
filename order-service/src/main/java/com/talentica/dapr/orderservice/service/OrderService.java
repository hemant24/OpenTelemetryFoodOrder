package com.talentica.dapr.orderservice.service;

import com.talentica.dapr.orderservice.controller.dto.OrderViewDto;
import com.talentica.dapr.orderservice.controller.mapper.OrderMapper;
import com.talentica.dapr.orderservice.event.DomainEventPublisher;
import com.talentica.dapr.orderservice.event.OrderCancelled;
import com.talentica.dapr.orderservice.event.OrderCreated;
import com.talentica.dapr.orderservice.repository.OrderRepository;
import com.talentica.dapr.orderservice.repository.entity.Order;
import com.talentica.dapr.orderservice.repository.entity.OrderLineItem;
import com.talentica.dapr.orderservice.service.external.RestaurantClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderService {
    @Autowired
    OrderRepository repository;
    @Autowired
    RestaurantClient restaurantClient;
    @Autowired
    DomainEventPublisher eventPublisher;

    /*@Autowired
    StateStoreService stateStoreService;*/
    public String createOrder(Order order){
        RestaurantClient.Restaurant restaurant = null;
        try {
            restaurant = restaurantClient.get(order.getRestaurantId());
        }catch (Exception e){
            log.error("error while fetching restaurant details", e);
            throw e;
        }
        Map<String, RestaurantClient.MenuItemViewDto> menuMap = restaurant.
                getMenuItems().stream().collect(Collectors.toMap(mI -> mI.getMenuItemId(), mI -> mI));
        for(OrderLineItem item : order.getItems()) {
            if (!menuMap.containsKey(item.getMenuItemId())) {
                throw new RuntimeException("Invalid menu item");
            }
        }
        for(OrderLineItem item : order.getItems()){
            item.setOrder(order);
            item.setName(menuMap.get(item.getMenuItemId()).getName());
            item.setPrice(menuMap.get(item.getMenuItemId()).getPrice());
        }
        repository.save(order);
        String orderId = order.getOrderId();
        eventPublisher.publish("ORDER", orderId, new OrderCreated(orderId, order.getRestaurantId()));
        //stateStoreService.save("OrderId", orderId, 60);
        return orderId;
    }
    public OrderViewDto getOrder(String orderId){
        Order order = repository.findByOrderId(orderId);
        if(ObjectUtils.isEmpty(order)){
            throw new RuntimeException("Order not found");
        }
        return OrderMapper.INSTANCE.toDto(order);
    }
    public void cancelOrder(String orderId) {
       /* if(orderId.equals(stateStoreService.get("OrderId"))){
            log.info(orderId + ", Order cancelled successfully.");
            eventPublisher.publish("ORDER", orderId, new OrderCancelled(orderId));
            stateStoreService.remove("OrderId");
        }else {
            throw new RuntimeException("Order can not be cancelled now.");
        }*/
    }
}
