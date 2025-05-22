package com.example.api_movie.repository;

import com.example.api_movie.model.FoodBooking;
import com.example.api_movie.model.FoodBookingKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodBookingRepository extends JpaRepository<FoodBooking, FoodBookingKey> {
}
