package com.example.api_movie.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CouponDto {
    private String couponCode;
    private BigDecimal balance;
    private LocalDate expiry;
}
