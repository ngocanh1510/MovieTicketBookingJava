package com.example.api_movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.api_movie.model.Theater;

import java.util.List;

public interface TheaterRepository extends JpaRepository<Theater, Integer> {
    List<Theater> findAllByOrderByNameAsc();
}
