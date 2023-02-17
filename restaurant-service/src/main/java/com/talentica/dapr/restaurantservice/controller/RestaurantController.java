package com.talentica.dapr.restaurantservice.controller;


import com.talentica.dapr.restaurantservice.controller.dto.CreateRestaurantDto;
import com.talentica.dapr.restaurantservice.controller.dto.RestaurantViewDto;
import com.talentica.dapr.restaurantservice.repository.entity.Restaurant;
import com.talentica.dapr.restaurantservice.service.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/restaurant")
@Slf4j
public class RestaurantController {

  @Autowired
  RestaurantService service;

  @PostMapping
  CreateRestaurantDto createRestaurant(@RequestBody Restaurant restaurant){
    return new CreateRestaurantDto(service.createRestaurant(restaurant));
  }

  @GetMapping("/{restaurantId}")
  RestaurantViewDto getOrder(@PathVariable("restaurantId") String restaurantId){
    return service.getRestaurant(restaurantId);
  }

}
