package com.example.api_movie.controller;

import com.example.api_movie.dto.MovieDto;
import com.example.api_movie.model.Movie;
import com.example.api_movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public List<MovieDto> getAllMovies() {
        return movieService.getAllMovies();
    }

    @PostMapping
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<MovieDto> addMovie(@RequestBody MovieDto movieDto) {
        MovieDto createdMovie = movieService.addMovie(movieDto);
        return ResponseEntity.ok(createdMovie);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<MovieDto> updateMovie(@PathVariable int id,@RequestBody MovieDto movieDto) {
        MovieDto updatedMovie = movieService.updateMovie(id, movieDto);
        return ResponseEntity.ok(updatedMovie);
    }
}
