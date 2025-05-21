package com.example.api_movie.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
    private Integer userId;
    private String paymentStatus;
    private List<TicketDto> tickets;
    private BigDecimal totalPrice;
}
