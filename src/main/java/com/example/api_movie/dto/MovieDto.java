package com.example.api_movie.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {
    private int id;
    private String title;
    private LocalDate releaseDate;
    private int duration;
    private String genre;
    private int limitAge;
    private String poster;
    private double rating;
    private String filmDirector;
    private String descriptionMovie;
    private String castMovie;
    private String vidUrl;
}
