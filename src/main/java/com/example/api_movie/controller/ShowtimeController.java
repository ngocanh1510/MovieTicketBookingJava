package com.example.api_movie.controller;

import com.example.api_movie.dto.ShowtimeDto;
import com.example.api_movie.service.ShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/showtimes")
public class ShowtimeController {
    @Autowired
    private ShowtimeService showtimeService;

    @GetMapping
    public List<ShowtimeDto> getAllShowtimes() { return showtimeService.getAllMovies();}

    @GetMapping("/title/{title}")
    public List<ShowtimeDto> getShowtimeByMovieTitle(@PathVariable String title) {
        return showtimeService.getShowtimeByMovieTitle(title);
    }

}
