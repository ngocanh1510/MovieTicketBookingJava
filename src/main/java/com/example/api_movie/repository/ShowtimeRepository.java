package com.example.api_movie.repository;

import com.example.api_movie.model.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowtimeRepository extends JpaRepository<Showtime,Integer> {
    List<Showtime> findAllByOrderByMovieTitleAsc();
    List<Showtime> findShowtimeByMovie_Title(String title);
}
