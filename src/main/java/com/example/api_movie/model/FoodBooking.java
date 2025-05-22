package com.example.api_movie.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "Food_Booking")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FoodBooking {
    @EmbeddedId
    @JsonIgnore
    private FoodBookingKey id;

    @ManyToOne
    @MapsId("foodId")
    @JoinColumn(name = "food_id")
    private Food food;

    @ManyToOne
    @MapsId("bookingId")
    @JoinColumn(name = "booking_id")
    @JsonIgnore
    private Booking booking;

    private int quantity;
}


