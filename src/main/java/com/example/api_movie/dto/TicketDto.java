package com.example.api_movie.dto;

import java.math.BigDecimal;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketDto {
    private int id;
    private int userId;
    private Integer seatId;
    private Integer showtimeId;
    private BigDecimal price;
    private int bookingId;
}
