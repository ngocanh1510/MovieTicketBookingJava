package com.example.api_movie.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "Seats")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;
    private char row;
    private int number;
    @Enumerated(EnumType.STRING)
    private Status status;
    private BigDecimal price;

    public enum Status {
        available,
        selected,
        booked,
    }


}
