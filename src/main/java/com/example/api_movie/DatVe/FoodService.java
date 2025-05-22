package com.example.api_movie.DatVe;

import com.example.api_movie.dto.FoodDto;
import com.example.api_movie.model.Food;
import com.example.api_movie.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodService {
    @Autowired
    private FoodRepository foodRepository;

    public List<FoodDto> findAll() {
        return foodRepository.findAllByOrderByCategoryAsc().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<FoodDto> findByCategory(Food.Category category) {
        return foodRepository.findByCategory(category).stream().map(this::convertToDto).collect(Collectors.toList());
    }
    private FoodDto convertToDto(Food food) {
        return new FoodDto(
                food.getCategory(),
                food.getName(),
                food.getPrice(),
                food.getDetail(),
                food.getImg()
        );
    }
}
