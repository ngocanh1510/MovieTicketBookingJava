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

    // Lấy thông tin phim
    public List<MovieDto> getAllMovies() {
        return movieRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Thêm phim của admin
    public MovieDto addMovie(MovieDto movieDto) {
        Movie movie = new Movie();
        movie.setTitle(movieDto.getTitle());
        movie.setReleaseDate(movieDto.getReleaseDate());
        movie.setDuration(movieDto.getDuration());
        movie.setGenre(movieDto.getGenre());
        movie.setLimitAge(movieDto.getLimitAge());
        movie.setPoster(movieDto.getPoster());
        movie.setRating(movieDto.getRating());
        movie.setFilmDirector(movieDto.getFilmDirector());
        movie.setDescriptionMovie(movieDto.getDescriptionMovie());
        movie.setCastMovie(movieDto.getCastMovie());
        movie.setVidUrl(movieDto.getVidUrl());

        Movie savedMovie = movieRepository.save(movie);
        return convertToDto(savedMovie);
    }

    // Cập phim của admin
    public MovieDto updateMovie(int id, MovieDto movieDto) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy movie có id: " + id));

        movie.setTitle(movieDto.getTitle());
        movie.setReleaseDate(movieDto.getReleaseDate());
        movie.setDuration(movieDto.getDuration());
        movie.setGenre(movieDto.getGenre());
        movie.setLimitAge(movieDto.getLimitAge());
        movie.setPoster(movieDto.getPoster());
        movie.setRating(movieDto.getRating());
        movie.setFilmDirector(movieDto.getFilmDirector());
        movie.setDescriptionMovie(movieDto.getDescriptionMovie());
        movie.setCastMovie(movieDto.getCastMovie());
        movie.setVidUrl(movieDto.getVidUrl());
        Movie savedMovie = movieRepository.save(movie);
        return convertToDto(savedMovie);
    }

    // Xóa phim của admin
    public void deleteMovie(int id) {
        if (!movieRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy movie có id: " + id);
        }
        movieRepository.deleteById(id);
    }

    // Lấy thông tin phim theo Id
    public MovieDto getMovieById(int id) {
        if (!movieRepository.existsById(id)) {
            throw new RuntimeException("Không tim thấy movie có id: " + id);
        }
        return convertToDto(movieRepository.findById(id).get());

    }

    public List<MovieDto> findMoviesByTitle(String title) {
        List<Movie> movies = movieRepository.findByTitleContainingIgnoreCase(title);
        return movies.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
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
