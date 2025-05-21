package com.example.api_movie.service;

import com.example.api_movie.dto.TheaterDto;
import com.example.api_movie.model.Theater;
import com.example.api_movie.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TheaterService {
    @Autowired
    private TheaterRepository theaterRepository;

    public List<TheaterDto> getAllTheaters() {
        return theaterRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private TheaterDto convertToDto(Theater theater) {
        return new TheaterDto(
                theater.getName(),
                theater.getLocation(),
                theater.getBrand().getName()
        );
    }
}
