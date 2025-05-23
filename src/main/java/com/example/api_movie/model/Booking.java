package com.example.api_movie.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

import com.example.api_movie.model.Ticket;
import lombok.*;

@Entity
@Table(name = "Booking")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;


    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<FoodBooking> foodBookings;

    @Column(name = "payment_status")
    private String paymentStatus;
    private BigDecimal total;
}
