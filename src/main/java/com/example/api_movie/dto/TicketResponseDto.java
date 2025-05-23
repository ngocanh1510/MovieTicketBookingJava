package com.example.api_movie.dto;

import java.math.BigDecimal;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponseDto {
    private Integer id;
    private String seatName;
    private ShowtimeResponseDto showtime;
    private BigDecimal price;
}
