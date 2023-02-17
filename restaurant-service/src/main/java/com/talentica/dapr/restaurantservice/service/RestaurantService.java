package com.talentica.dapr.restaurantservice.service;

import com.talentica.dapr.restaurantservice.controller.dto.RestaurantViewDto;
import com.talentica.dapr.restaurantservice.controller.mapper.RestaurantMapper;
import com.talentica.dapr.restaurantservice.repository.RestaurantRepository;
import com.talentica.dapr.restaurantservice.repository.entity.MenuItem;
import com.talentica.dapr.restaurantservice.repository.entity.Restaurant;
import com.talentica.dapr.restaurantservice.service.external.LocalProxy;
import com.talentica.dapr.restaurantservice.service.external.RatingClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class RestaurantService {

    @Autowired
    RestaurantRepository repository;

    @Autowired
    RatingClient ratingClient;

    @Autowired
    LocalProxy localProxy;

    public String createRestaurant(Restaurant restaurant){
        for(MenuItem item : restaurant.getMenuItems()){
            item.setRestaurant(restaurant);
        }
        repository.save(restaurant);
        return restaurant.getRestaurantId();
    }
    public RestaurantViewDto getRestaurant(String restaurantId){
        Restaurant restaurant = repository.findByRestaurantId(restaurantId);
        try {
            RatingClient.RatingDto ratingDto = ratingClient.get(restaurantId);

            restaurant.setRating(ratingDto.getRating());
        }catch (Exception e){
            log.error("Error while fetching rating for a restaurant ", e);
        }
        return RestaurantMapper.INSTANCE.toDto(restaurant);
    }

    public void placeOrderAtRestaurant(String orderId){
        Map map = localProxy.getSecret("apiKey");
        System.out.println("API key to call exteral api is : " + map.get("apiKey"));
    }
}
