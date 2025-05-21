package com.example.api_movie.model;

import jakarta.persistence.*;
import lombok.*;
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
    @Enumerated(EnumType.STRING) // Lưu enum dưới dạng chuỗi trong DB
    private Status status;
    private int price;

    public enum Status {
        available,
        selected,
        booked,
    }

}
