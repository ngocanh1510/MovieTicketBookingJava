package com.example.api_movie.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Coupons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate expiry;
    private BigDecimal balance;

    @Column(name = "title")
    private String couponCode;
}
