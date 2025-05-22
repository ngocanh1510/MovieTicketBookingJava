package com.example.api_movie.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class FoodBookingKey implements Serializable {
    private int foodId;
    private int bookingId;

}