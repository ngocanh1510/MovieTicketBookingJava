package com.example.api_movie.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShowtimeDto {
    private int movieId;
    private String movieTitle;
    private int roomId;
    private String roomName;
    private LocalDate date;
    private LocalTime time;
    private String language;

    public ShowtimeDto(String movieTitle, String roomName, LocalDate date, LocalTime time, String language) {
        this.movieTitle = movieTitle;
        this.roomName = roomName;
        this.date = date;
        this.time = time;
        this.language = language;
    }

    public ShowtimeDto(int movieId, int roomId, LocalDate date, LocalTime time, String language) {
        this.movieId = movieId;
        this.roomId = roomId;
        this.date = date;
        this.time = time;
        this.language = language;
    }
}
