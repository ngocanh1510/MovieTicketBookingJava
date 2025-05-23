package com.example.api_movie.dto;

import com.example.api_movie.model.Food;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class FoodDto {
    @Enumerated(EnumType.STRING)
    private Food.Category category;
    private String name;
    private BigDecimal price;
    private String detail;
    private String img;
}
