package com.example.api_movie.dto;

import com.example.api_movie.model.FoodBooking;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseDto {
    private Integer id;
    private String paymentStatus;
    private List<TicketResponseDto> tickets;
    private List<FoodBooking> foodBookings;
    private BigDecimal total;
}
