package com.example.api_movie.dto;

import com.example.api_movie.model.Seat;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class SeatDto {
    private int roomId;
    private String roomName;
    private char row;
    private int number;
    @Enumerated(EnumType.STRING)
    private Seat.Status status;
    private BigDecimal price;
}
