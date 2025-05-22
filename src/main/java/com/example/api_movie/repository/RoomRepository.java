package com.example.api_movie.repository;

import com.example.api_movie.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository <Room, Integer> {
    List<Room> findAllByOrderByTheaterNameAsc();
    List<Room> findByTheaterName(String theaterName);

    Room findByName(String name);
}
