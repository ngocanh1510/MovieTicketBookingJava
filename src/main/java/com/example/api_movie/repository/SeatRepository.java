package com.example.api_movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.api_movie.model.Seat;
import java.util.List;
public interface SeatRepository extends JpaRepository<Seat, Integer> {
    public List<Seat> findSeatsByRoom_Name(String roomName);
}
