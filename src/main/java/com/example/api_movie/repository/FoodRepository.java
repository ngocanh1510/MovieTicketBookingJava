package com.example.api_movie.repository;

import com.example.api_movie.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Integer> {
    List<Food> findAllByOrderByCategoryAsc();

    List<Food> findByCategory(Food.Category category);
}
