package com.talentica.dapr.restaurantservice.repository;

import com.talentica.dapr.restaurantservice.repository.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    Restaurant findByRestaurantId(String id);
}
