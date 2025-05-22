package com.example.api_movie.controller;

import com.example.api_movie.dto.FoodDto;
import com.example.api_movie.model.Food;
import com.example.api_movie.DatVe.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
public class FoodController {
    @Autowired
    private FoodService foodService;

    @GetMapping
    public List<FoodDto> getAll() {
        return foodService.findAll();
    }

    @GetMapping("/{category}")
    public List<FoodDto> getByCategory(@PathVariable Food.Category category) {
        return foodService.findByCategory(category);
    }
}
