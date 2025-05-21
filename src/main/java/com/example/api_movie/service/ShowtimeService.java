package com.example.api_movie.service;

import com.example.api_movie.dto.ShowtimeDto;
import com.example.api_movie.model.Showtime;
import com.example.api_movie.repository.ShowtimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowtimeService {
    @Autowired
    private ShowtimeRepository showtimeRepository;
    public List<ShowtimeDto> getAllMovies() {
        return showtimeRepository.findAllByOrderByMovieTitleAsc().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<ShowtimeDto> getShowtimeByMovieTitle(String title){
        return showtimeRepository.findShowtimeByMovie_Title(title).stream().map(this::convertToDto).collect(Collectors.toList());
    }
    private ShowtimeDto convertToDto(Showtime showtime) {
        return new ShowtimeDto(
                showtime.getMovie().getTitle(),
                showtime.getRoom().getName(),
                showtime.getDate(),
                showtime.getTime(),
                showtime.getLanguage()
        );
    }

}
