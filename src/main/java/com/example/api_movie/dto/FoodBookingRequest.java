package com.example.api_movie.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FoodBookingRequest {
    private int foodId;
    private int quantity;
}
