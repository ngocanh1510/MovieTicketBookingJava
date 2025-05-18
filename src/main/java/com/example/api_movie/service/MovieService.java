package com.example.api_movie.service;

import com.example.api_movie.dto.MovieDto;
import com.example.api_movie.model.Movie;
import com.example.api_movie.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public List<MovieDto> getAllMovies() {
        return movieRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private MovieDto convertToDto(Movie movie) {
        return new MovieDto(
                movie.getId(),
                movie.getTitle(),
                movie.getReleaseDate(),
                movie.getDuration(),
                movie.getGenre(),
                movie.getLimitAge(),
                movie.getPoster(),
                movie.getRating(),
                movie.getFilmDirector(),
                movie.getDescriptionMovie(),
                movie.getCastMovie(),
                movie.getVidUrl()
        );
    }
}
