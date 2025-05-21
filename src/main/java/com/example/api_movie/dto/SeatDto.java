package com.example.api_movie.dto;

import com.example.api_movie.model.Seat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class SeatDto {

    private String roomName;
    private char row;
    private int number;
    @Enumerated(EnumType.STRING)
    private Seat.Status status;
    private int price;
}
