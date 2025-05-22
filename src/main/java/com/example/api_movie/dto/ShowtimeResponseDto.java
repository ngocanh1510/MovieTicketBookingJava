package com.example.api_movie.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShowtimeResponseDto {
    private String movieTitle;
    private String roomName;
    private LocalDate date;
    private LocalTime time;
    private String language;
}
