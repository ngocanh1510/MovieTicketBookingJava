package com.example.api_movie.controller;


import com.example.api_movie.dto.TheaterDto;
import com.example.api_movie.model.Theater;
import com.example.api_movie.service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theaters")
public class TheaterController {
    @Autowired
    private TheaterService theaterService;

    @GetMapping
    public List<TheaterDto> getAllTheaters() {
        return theaterService.getAllTheaters();
    }
}
