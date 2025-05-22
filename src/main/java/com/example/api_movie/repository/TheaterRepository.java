package com.example.api_movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.api_movie.model.Theater;

public interface TheaterRepository extends JpaRepository<Theater, Integer> {
    Theater findByName(String name);
}
